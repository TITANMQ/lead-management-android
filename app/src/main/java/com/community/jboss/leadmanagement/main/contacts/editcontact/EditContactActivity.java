package com.community.jboss.leadmanagement.main.contacts.editcontact;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.community.jboss.leadmanagement.R;
import com.community.jboss.leadmanagement.data.entities.ContactNumber;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.community.jboss.leadmanagement.SettingsActivity.PREF_DARK_THEME;

public class EditContactActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_CONTACT_NUM = "INTENT_EXTRA_CONTACT_NUM";

    @BindView(R.id.add_contact_toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView((R.id.contact_avatar))
    ImageView contactAvatar;
    @BindView(R.id.select_image_btn)
    Button select_image_btn;
    @BindView(R.id.contact_name_field)
    EditText contactNameField;
    @BindView(R.id.contact_number_field)
    EditText contactNumberField;
    @BindView(R.id.contact_email_field)
    EditText contactEmailField;
    @BindView(R.id.contact_notes_field)
    EditText contactNotesField;

    private EditContactActivityViewModel mViewModel;
    private boolean image = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_BG);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact);

        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(EditContactActivityViewModel.class);
        mViewModel.getContact().observe(this, contact -> {
            if (contact == null || mViewModel.isNewContact()) {
                setTitle(R.string.title_add_contact);
                image = false;
            } else {
                setTitle(R.string.title_edit_contact);
                contactNameField.setText(contact.getName());
                contactAvatar.setImageBitmap(contact.getAvatarBitmap());
                contactEmailField.setText(contact.getEmail());
                image = true;
            }
        });
        mViewModel.getContactNumbers().observe(this, contactNumbers -> {
            if (contactNumbers == null || contactNumbers.isEmpty()) {
                return;
            }
            // Get only the first one for now
            final ContactNumber contactNumber = contactNumbers.get(0);
            contactNumberField.setText(contactNumber.getNumber());
        });

        final Intent intent = getIntent();
        final String number = intent.getStringExtra(INTENT_EXTRA_CONTACT_NUM);
        if(mViewModel.getContactNumberByNumber(number)!=null){
            mViewModel.setContact(mViewModel.getContactNumberByNumber(number).getContactId());
        }else{
            mViewModel.setContact(null);
            contactNumberField.setText(number);
        }

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp));
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(image)
        {
            contactAvatar.setVisibility(View.INVISIBLE);
            select_image_btn.setVisibility(View.VISIBLE);
        }
        else
            {
                contactAvatar.setVisibility(View.VISIBLE);
                select_image_btn.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                saveContact();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_contact, menu);
        return true;
    }



    //TODO Add multiple numbers
    private void saveContact() {
        // Check is Name or Password is empty
        if (!checkEditText(contactNameField, "Please enter name")||!checkNo(contactNumberField,"Enter Correct no.")
                || !checkEditText(contactNumberField, "Please enter number")||
                !checkEditText(contactEmailField, "Please enter email address")) {
            return;
        }


        final String name = contactNameField.getText().toString();
        final Drawable avatar = contactAvatar.getDrawable();
        final String email = contactEmailField.getText().toString();
        mViewModel.saveContact(name,avatar, email);

        final String number = contactNumberField.getText().toString();
        mViewModel.saveContactNumber(number);







        finish();
    }

    private boolean checkEditText(EditText editText, String errorStr) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(errorStr);
            return false;
        }

        return true;
    }
    private boolean checkNo(EditText editText, String errorStr) {
        if (editText.getText().toString().length() < 4) {
            editText.setError(errorStr);
            return false;
        }
        return true;
    }

    private void setContactImage()
    {

    }

    public void onClick(View view)
    {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK) {
            Uri selectedfile = data.getData(); //The uri with the location of the file

            contactAvatar.setImageURI(selectedfile);
            contactAvatar.setVisibility(View.VISIBLE);
            select_image_btn.setText("Change Image");
            contactAvatar.getDrawable();
        }
    }
}
