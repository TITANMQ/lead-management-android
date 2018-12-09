package com.community.jboss.leadmanagement.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by carbonyl on 09/12/2017.
 */
@Entity
public class Contact {
    @PrimaryKey @NonNull
    private final String id;
    private String name;
    private byte[] avatar;
    private String email;


    @Ignore
    public Contact(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.mail = "";
    }
    @Ignore
    public Contact(@NonNull String id, String name) {
        this.id = id;
        this.name = name;

    }

    public Contact(@NonNull String id, String name, byte[] avatar, String email) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getQuery() {
        return query;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public Bitmap getAvatarBitmap() {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);

        return bitmap;

    }

    public void setAvatar(Drawable avatar) {
        Bitmap bitmap = ((BitmapDrawable) avatar).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.avatar = stream.toByteArray();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
