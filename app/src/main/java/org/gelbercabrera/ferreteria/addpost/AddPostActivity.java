package org.gelbercabrera.ferreteria.addpost;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.gelbercabrera.ferreteria.R;
import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.main.ui.MainActivity;
import org.gelbercabrera.ferreteria.messages.chat.ui.ChatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPostActivity extends AppCompatActivity implements AddPostView {
    private static final int SELECT_PHOTO = 100;

    @Bind(R.id.editTxtName)
    EditText editTxtName;
    @Bind(R.id.editTxtDescription)
    EditText editTextDescription;
    @Bind(R.id.editTxtPrice)
    EditText editTextPrice;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnAccept)
    Button btnAccept;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.btnImage)
    Button btnUpload;

    private static AddPostActivity INSTANCE;

    public static AddPostActivity getINSTANCE() {
        return INSTANCE;
    }

    private AddPostPresenter presenter;

    public AddPostActivity() {
        presenter = new AddPostPresenterImpl(this);
    }

    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        setTitle("Agregar artículo en venta");
        ButterKnife.bind(this);
        INSTANCE = this;
        setProperties();
    }

    @Override
    public void showInput() {
        editTextDescription.setVisibility(View.VISIBLE);
        editTextPrice.setVisibility(View.VISIBLE);
        editTxtName.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        editTextDescription.setVisibility(View.GONE);
        editTextPrice.setVisibility(View.GONE);
        editTxtName.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void postAdded() {
        Toast.makeText(this, "Articulo agregado", Toast.LENGTH_SHORT).show();
    }

    public void setProperties(){
        final AddPostActivity activity = this;
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                if (editTextDescription.getText().toString().isEmpty() ||
                        editTextPrice.getText().toString().isEmpty() ||
                        editTxtName.getText().toString().isEmpty() || imageView.getDrawingCache() == null){
                    new android.app.AlertDialog.Builder(activity)
                            .setTitle("Datos incompletos")
                            .setMessage("Por favor complete todos los campos")
                            .setPositiveButton("ACEPTAR", null)
                            .show();
                }else {
                    post();
                }
            }
        });
    }

    public void post(){
        final Post post = new Post();
        post.setUrlImage(null);

        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String name = editTxtName.getText().toString()+Math.random();
        StorageReference reference = FirebaseHelper.getInstance().getImagesRef().child(name.replace(" ","").replace(".",""));

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

            }
        });
        post.setUrlImage(reference.getName());
        post.setDescripction(editTextDescription.getText().toString());
        post.setName(editTxtName.getText().toString());
        post.setPrice(editTextPrice.getText().toString());
        post.setDate(new Date());
        post.setEmail_poster(FirebaseHelper.getInstance().getAuthUserEmail().replace(".","_"));
        FirebaseHelper.getInstance().getMyUserReference().child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post.setName_poster(dataSnapshot.getValue().toString());
                presenter.addPost(post);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        startActivity(new Intent(AddPostActivity.getINSTANCE(),MainActivity.class));
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.btnImage)
    public void pickAImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageURI(selectedImage);// To display selected image in image view
                }
        }
    }
}
