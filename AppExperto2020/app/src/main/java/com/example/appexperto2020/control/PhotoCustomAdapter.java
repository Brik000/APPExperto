package com.example.appexperto2020.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.appexperto2020.R;

import java.util.ArrayList;

public class PhotoCustomAdapter extends BaseAdapter {

    private ArrayList<PhotoAdapter> photos;

    public PhotoCustomAdapter()
    {
        photos = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.image_line, null);
        ImageView image = row.findViewById(R.id.image);
        Bitmap i = BitmapFactory.decodeFile(photos.get(position).getFile().getPath());
        Bitmap bitmap = Bitmap.createScaledBitmap(i,
                i.getWidth()/4,
                i.getHeight()/4,
                false);
        image.setImageBitmap(bitmap);


        return row;
    }

    public void addPhoto(PhotoAdapter photo)
    {
        photos.add(photo);
        this.notifyDataSetChanged();

    }
}
