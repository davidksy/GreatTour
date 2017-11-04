package com.example.a60047506.greattour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReviewActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAMERA = 11;
    private static final int REQUEST_IMAGE_ALBUM = 12;
    Intent intent;
    File tempFile;
    String tempPath;
    boolean ret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
    }

    public void onBtnReview(View v)
    {
         intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_ALBUM);

    }

    public void onBtnAdd(View v)
    {
        if(tempPath != null)
        {
            EditText review_edit = (EditText)findViewById(R.id.review_text);

            String review_txt = review_edit.getText().toString();

            new ImageUpload().execute(
                    "http://13.125.37.8:52273/user/picture",
                    tempPath, review_txt);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_IMAGE_ALBUM:
                Log.i("resultCode", resultCode+"");
                if (resultCode == RESULT_OK)
                {
                    String mCurrentPhotoPath = getPathFromUri(data.getData());
                    Log.i("mCurrentPhotoPath", mCurrentPhotoPath);
                    //File tempFile = new File(mCurrentPhotoPath);

                    tempFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    //tempFile = new File(tempFile.getAbsolutePath()+"temp.jpg");
                    tempPath = tempFile.getAbsolutePath()+"/temp.jpg";
                    tempFile= new File(tempPath);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap bitmap  = BitmapFactory.decodeFile(mCurrentPhotoPath, options);


                    try{
                        tempFile.createNewFile();  // 파일을 생성해주고

                        FileOutputStream out = new FileOutputStream(tempFile);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 , out);  // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌

                        out.close(); // 마무리로 닫아줍니다.

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
        }
    }


    public String getPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
        return path;
    }

    class ImageUpload extends AsyncTask<String,String,String> {
        ProgressDialog dialog = new ProgressDialog(ReviewActivity.this);
        @Override
        protected String doInBackground(String... params) {
            StringBuilder output = new StringBuilder();

            DataOutputStream dos = null;
            ByteArrayInputStream bis = null;
            ByteArrayInputStream bis2 = null;
            InputStream is = null;

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            try{

                URL url = new URL(params[0]);
                FileInputStream fstrm = new FileInputStream(params[1]);
                String filename = new File(params[1]).getName();
                String description = params[2];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true); conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                // write data
                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"description\"");
                dos.writeBytes(lineEnd + lineEnd + description + lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition:form-data;name=\"image\";filename=\"" + filename + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = fstrm.available();
                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                int bytesRead = fstrm.read( buffer , 0 , bufferSize);
                Log.e("File Up", "text byte is " + bytesRead );
                while(bytesRead > 0 ){
                    dos.write(buffer , 0 , bufferSize);
                    bytesAvailable = fstrm.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fstrm.read(buffer,0,bufferSize);
                }

                fstrm.close();

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                Log.e("File Up" , "File is written");
                dos.flush();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line = null;
                while(true) {
                    line = reader.readLine();
                    if (line == null) break;
                    output.append(line);
                }
                reader.close();
                conn.disconnect();
                tempFile.delete();

            } catch(Exception e) {
                e.printStackTrace();
            }
            return output.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("이미지 업로드 중...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            Log.i("result json", s);
        }
    }

}
