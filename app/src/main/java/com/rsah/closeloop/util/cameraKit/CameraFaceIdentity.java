/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rsah.closeloop.util.cameraKit;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.ConsumerIrManager;
import android.media.ExifInterface;
import android.os.Bundle;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.material.snackbar.Snackbar;
import com.rsah.closeloop.Api.ApiService;
import com.rsah.closeloop.Api.Server;
import com.rsah.closeloop.Constant.Constant;
import com.rsah.closeloop.Menu.Payment.paymentSiswa;
import com.rsah.closeloop.Model.ModelInfran;
import com.rsah.closeloop.Model.ResponseDataInfran;
import com.rsah.closeloop.R;
import com.rsah.closeloop.Session.SessionManager;
import com.rsah.closeloop.util.Helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsah.closeloop.Menu.Payment.paymentSiswa.bitmapPreview;
import static com.rsah.closeloop.util.cameraKit.FaceGraphic.INFRAN_Message;
import static com.rsah.closeloop.util.cameraKit.FaceGraphic.INFRAN_NAME;


/**
 * Activity for the face tracker app.  This app detects faces with the rear facing camera, and draws
 * overlay graphics to indicate the position, size, and ID of each face.
 */
public final class CameraFaceIdentity extends AppCompatActivity {
    private static final String TAG = "FaceTracker";

    private CameraSource mCameraSource = null;

    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    private TextView tvDetect ;
    private ApiService API;
    private Context mContext ;
    public List<ModelInfran> EntityInfran = new ArrayList<>();
    private SessionManager sessionManager ;

    private int numShutter = 0 ;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.camera_identity);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
        tvDetect = findViewById(R.id.tvtDetect);
        API = Server.getAPIServiceInfran();
        mContext = this ;
        sessionManager = new SessionManager(mContext);




        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }






        }


    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }




        mCameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(30.0f)

                .build();



    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Detection")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay, CameraFaceIdentity.this);
        }
    }


    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay, Context context) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay,context);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {

            numShutter +=1 ;

            if (numShutter > 2){

                Toast.makeText(mContext, "Face Not Detected, Please, Try Again", Toast.LENGTH_SHORT).show();
                finish();

            }




            mFaceGraphic.setId(faceId);

            // delay 2 second
            try {
                TimeUnit.SECONDS.sleep(2);//delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tvDetect.setText("Wajah Terdeteksi");
            tvDetect.setTextColor(getResources().getColor(R.color.colorPrimary));


            //for shutter
            captureImage();






        }

        private void captureImage() {
            mPreview.setDrawingCacheEnabled(true);
            final Bitmap drawingCache = mPreview.getDrawingCache();

            try {
                TimeUnit.SECONDS.sleep(2);//delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mCameraSource.takePicture(null, new CameraSource.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes) {
                    int orientation = Exif.getOrientation(bytes);
                    Bitmap temp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap picture = rotateImage(temp,orientation);
                    Bitmap overlay = Bitmap.createBitmap(mGraphicOverlay.getWidth(),mGraphicOverlay.getHeight(),picture.getConfig());
                    Canvas canvas = new Canvas(overlay);

                    Matrix matrix = new Matrix();

                    matrix.setScale((float)overlay.getWidth()/(float)picture.getWidth(),(float)overlay.getHeight()/(float)picture.getHeight());

                    // mirror by inverting scale and translating
                    matrix.preScale(-1, 1);
                    matrix.postTranslate(canvas.getWidth(), 0);

                    Paint paint = new Paint();
                    canvas.drawBitmap(picture,matrix,paint);
                    canvas.drawBitmap(drawingCache,0,0,paint);

                    //send bitmap for tes preview
                    bitmapPreview = overlay ;

                    picture.recycle();
                    drawingCache.recycle();
                    mPreview.setDrawingCacheEnabled(false);




                    Bitmap cropped = Helper.cropToSquare(overlay);



                    //request to infran
                    String data = Helper.getStringImage(cropped);
                    String hash = Helper.Hash_SHA256(data);
                    requestInfran(data,hash);







                    // for save
                   /* try {
                        String mainpath = getExternalStorageDirectory() + separator + "MaskIt" + separator + "images" + separator;
                        File basePath = new File(mainpath);
                        if (!basePath.exists())
                            Log.d("CAPTURE_BASE_PATH", basePath.mkdirs() ? "Success": "Failed");
                        String path = mainpath + "photo_"  + ".jpg";
                        File captureFile = new File(path);
                        captureFile.createNewFile();
                        if (!captureFile.exists())
                            Log.d("CAPTURE_FILE_PATH", captureFile.createNewFile() ? "Success": "Failed");
                        FileOutputStream stream = new FileOutputStream(captureFile);
                        overlay.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        stream.flush();
                        stream.close();
                        picture.recycle();
                        drawingCache.recycle();
                        mPreview.setDrawingCacheEnabled(false);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        }




        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);

        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }


    private Bitmap rotateImage(Bitmap bm, int i) {
        Matrix matrix = new Matrix();
        switch (i) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bm;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bm;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            bm.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }








    private void requestInfran(String data,  String hash ){

        EntityInfran.clear();

        Helper.writeToFile(data,this);

        Call<ResponseDataInfran> call = API.requestInfran(new ModelInfran(data,hash));
        call.enqueue(new Callback<ResponseDataInfran>() {
            @Override
            public void onResponse(Call<ResponseDataInfran> call, Response<ResponseDataInfran> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() != null) {

                        String err_code = response.body().getErr_code() ;
                        String status = response.body().getStatus();


                        if(err_code.equals(Constant.ERR_0)){

                            String pip = response.body().getData().get(0).getPerson_in_picture();

                            //solve nama = unknow
                            if (!pip.equals("unknown")){



                                try {

                                    INFRAN_Message = response.body().getData().get(0).getPerson_in_picture() ;
                                    INFRAN_NAME = response.body().getData().get(0).getPerson_in_picture() ;

                                }catch (Exception ex){
                                    Log.e(TAG, "onResponse: "+ ex.getMessage() );
                                }



                                sessionManager.saveUserObjectSiswa(Helper.ConvertResponseDataInfranToJson(response.body()));
                                startActivity(new Intent(CameraFaceIdentity.this, paymentSiswa.class));
                                finish();

                            }

                            INFRAN_Message = "unknown" ;


                        }else{
                            INFRAN_Message = status ;
                            Toast.makeText(mContext, status, Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        Log.e(TAG, "onResponse: "+response.body().toString() );
                    }

                }else{

                    Log.e(TAG, "onResponse: "+response.body().toString() );
                }
            }

            @Override
            public void onFailure(Call<ResponseDataInfran> call, Throwable t) {

                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
















}
