package com.example.binhluc.notepad3a;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PhotoShootFragment extends Fragment implements onPhotoshootclicklistener,
        PopupMenu.OnMenuItemClickListener{
    private PhotoshootAdapter adapterPhoto;
    private RecyclerView recyclerViewPhoto;
    private ArrayList<PhotoShoot> photoshoots;
    private PhotoShootDAO photoDAO;
    private View rootView;
    private PopupMenu popup;
    private EditText searchText;

    public PhotoShootFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_photo_shoot, container, false);
        create();
        LoadAllNotes();
        setHasOptionsMenu(true);

        popup = new PopupMenu(getActivity().getApplicationContext(), getActivity().findViewById(R.id.add));
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.photo_popup_menu, popup.getMenu());
        setForceShowIcon(popup);

        return rootView;
    }
    private void create() {
        // 1. get a reference to recyclerView
        recyclerViewPhoto = (RecyclerView) rootView.findViewById(R.id.photos_list);
        searchText = (EditText) getActivity().findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            public void afterTextChanged(Editable s) {
                String getSearchText = searchText.getText().toString();
                if(!getSearchText.isEmpty()) {
                    LoadSearchNotes(getSearchText);
                }
                else
                {
                    LoadAllNotes();
                }
            }

        });
        // 2. set layoutManger
        recyclerViewPhoto.setLayoutManager(new LinearLayoutManager(getActivity()));
        photoDAO = PhotoShootDB.getInstance(getActivity()).photoShootDAO();
    }
    private void load() {

        // 3. create an adapter
        adapterPhoto = new PhotoshootAdapter(getActivity(), photoshoots);
        // 4. set adapter
        recyclerViewPhoto.setHasFixedSize(true);
        recyclerViewPhoto.setAdapter(adapterPhoto);
        adapterPhoto.setListener(this);
        // 5. set item animator to DefaultAnimator
        recyclerViewPhoto.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public void onResume() {
        super.onResume();
        LoadAllNotes();
    }
    public void LoadAllNotes()
    {
        this.photoshoots = new ArrayList<>();
        List<PhotoShoot> list = photoDAO.getAllNote();
        this.photoshoots.addAll(list);
        load();
    }
    public void LoadSearchNotes(String searchText1)
    {
        searchText1="%"+searchText1+"%";
        this.photoshoots = new ArrayList<>();
        List<PhotoShoot> list = photoDAO.getPhotoShootListSearch(searchText1);
        this.photoshoots.addAll(list);
        load();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                popup.show();
                popup.setOnMenuItemClickListener(this);
                return true;
        }
        return false;
    }

    @Override
    public void onPhotoClick(int noteid) {
        String id = Integer.toString(noteid);
        Intent intent = new Intent(getActivity(), PhotoshootEdit.class);
        intent.putExtra("CHOSEN_NOTE_ID", id);
        startActivity(intent);
    }
    // The method was compiled, not created by the owners.
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    private byte[] BitmapToByte(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Do not change
        image.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getActivity().getApplicationContext(), "Taking image selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                return true;
            case R.id.item2:
                Toast.makeText(getActivity().getApplicationContext(), "Choosing photo selected", Toast.LENGTH_LONG).show();
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 1);
                return true;
        }
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri image = data.getData();
            InputStream inputStream;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(image);
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                Toast.makeText(getActivity(), "Open file successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), PhotoshootEdit.class);
                byte[] imagebyte = BitmapToByte(bmp);
                intent.putExtra("IMAGE_BYTE_GALLERY", imagebyte);
                startActivity(intent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == 0) {
            Bundle extra = data.getExtras();
            Bitmap image = (Bitmap) extra.get("data");
            Intent intent = new Intent(getActivity().getApplicationContext(), PhotoshootEdit.class);
            Bundle extras = new Bundle();
            extras.putParcelable("IMAGE_BITMAP", image);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }
}
