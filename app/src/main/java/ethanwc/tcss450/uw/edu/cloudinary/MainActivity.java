package ethanwc.tcss450.uw.edu.cloudinary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button videoBtn, imageBtn, takeAPhoto;
    private ProgressBar progressBar;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int TAKEN_PHOTO_UPLOAD = 444;
    private int SELECT_IMAGE = 10;
    private int SELECT_VIDEO = 20;
    private ImageView img1, img2, img3;
    private File mPhoto;
    String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        progressBar = findViewById(R.id.progress_bar);
        videoBtn = findViewById(R.id.video);
        imageBtn = findViewById(R.id.img);
        takeAPhoto = findViewById(R.id.takephoto);


        MediaManager.init(this);

        takeAPhoto.setOnClickListener(this::dispatchTakePictureIntent);
        imageBtn.setOnClickListener(this::pickImageFromGallery);
        videoBtn.setOnClickListener(this::pickVideoFromGallery);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.e("PACKAGECHECK", "data check: " + data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            boolean r = data == null;

            Log.e("nullcheck", r + " does it?");


        }

        if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK) {

            Uri selectedVideo = data.getData();
            MediaManager.get()
                    .upload(selectedVideo)
                    .unsigned("u48dpnqx")
                    .option("resource_type", "video")
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Upload Started...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {

                            Toast.makeText(MainActivity.this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            videoBtn.setVisibility(View.INVISIBLE);

                            String publicId = resultData.get("public_id").toString();

                            String firstImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("12")
                                    .border("5px_solid_black").border("5px_solid_black")).resourceType("video")
                                    .generate(publicId + ".jpg");
                            Picasso.get().load(firstImgUrl).into(img1);

                            String secondImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("4")
                                    .width(200).height(150).radius(20).effect("saturation:50").border("5px_solid_black"))
                                    .resourceType("video").generate(publicId + ".jpg");
                            Picasso.get().load(secondImgUrl).into(img2);

                            String thirdImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("20")
                                    .width(200).height(150).radius(20).effect("grayscale").border("5px_solid_black").crop("crop"))
                                    .resourceType("video").generate(publicId + ".jpg");
                            Picasso.get().load(thirdImgUrl).into(img3);

                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                            Toast.makeText(MainActivity.this, "Upload Error", Toast.LENGTH_SHORT).show();
                            Log.v("ERROR!!", " VIDEO " + error.getDescription());
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }

                    }).dispatch();
        } else if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            MediaManager.get()
                    .upload(selectedImage)
                    .unsigned("u48dpnqx")
                    .option("resource_type", "image")
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Upload Started...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {

                            Toast.makeText(MainActivity.this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            videoBtn.setVisibility(View.INVISIBLE);

                            String publicId = resultData.get("public_id").toString();

                            String firstImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("12")
                                    .border("5px_solid_black").border("5px_solid_black")).resourceType("video")
                                    .generate(publicId + ".jpg");
                            Picasso.get().load(firstImgUrl).into(img1);

                            String secondImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("4")
                                    .width(200).height(150).radius(20).effect("saturation:50").border("5px_solid_black"))
                                    .resourceType("video").generate(publicId + ".jpg");
                            Picasso.get().load(secondImgUrl).into(img2);

                            String thirdImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("20")
                                    .width(200).height(150).radius(20).effect("grayscale").border("5px_solid_black").crop("crop"))
                                    .resourceType("video").generate(publicId + ".jpg");
                            Picasso.get().load(thirdImgUrl).into(img3);

                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                            Toast.makeText(MainActivity.this, "Upload Error", Toast.LENGTH_SHORT).show();
                            Log.v("ERROR!!", " IMAGE: " + error.getDescription());
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }

                    }).dispatch();
        } else if (requestCode == TAKEN_PHOTO_UPLOAD && resultCode == RESULT_OK) {

            galleryAddPic();
        } else {

            Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        "ethanwc.tcss450.uw.edu.cloudinary.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKEN_PHOTO_UPLOAD);
            }
        }
    }


    private void pickImageFromGallery(View view) {
        Intent GalleryIntent = new Intent();
        GalleryIntent.setType("image/*");
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(GalleryIntent, "select image"), SELECT_IMAGE);
    }

    private void pickVideoFromGallery(View view) {
        Intent GalleryIntent = new Intent();
        GalleryIntent.setType("video/*");
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(GalleryIntent, "select video"), SELECT_VIDEO);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        uploadPhoto(contentUri);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void uploadPhoto(Uri uri) {
        MediaManager.get()
                .upload(uri)
                .unsigned("u48dpnqx")
                .option("resource_type", "image")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Upload Started...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {

                        Toast.makeText(MainActivity.this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        videoBtn.setVisibility(View.INVISIBLE);

                        String publicId = resultData.get("public_id").toString();

                        String firstImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("12")
                                .border("5px_solid_black").border("5px_solid_black")).resourceType("video")
                                .generate(publicId + ".jpg");
                        Picasso.get().load(firstImgUrl).into(img1);

                        String secondImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("4")
                                .width(200).height(150).radius(20).effect("saturation:50").border("5px_solid_black"))
                                .resourceType("video").generate(publicId + ".jpg");
                        Picasso.get().load(secondImgUrl).into(img2);

                        String thirdImgUrl = MediaManager.get().url().transformation(new Transformation().startOffset("20")
                                .width(200).height(150).radius(20).effect("grayscale").border("5px_solid_black").crop("crop"))
                                .resourceType("video").generate(publicId + ".jpg");
                        Picasso.get().load(thirdImgUrl).into(img3);

                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {

                        Toast.makeText(MainActivity.this, "Upload Error", Toast.LENGTH_SHORT).show();
                        Log.v("ERROR!!", " IMAGE: " + error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }

                }).dispatch();
    }
}