package com.example.smsuygulama.ui.grupolustur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smsuygulama.GrupModel;
import com.example.smsuygulama.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GrupOlustur extends Fragment {
    EditText grupolusturgrupadieditText, grupolusturgrupaciklamaeditText;
    Button grupolusturgrupbutton;
    RecyclerView grupolusturreyclerview;
    ImageView grupolusturgrupsimgesi;

    Uri dosyayolu;

    ArrayList<GrupModel> grupModelArrayList;

    FirebaseAuth mAuth;
    FirebaseStorage mstorage;
    FirebaseFirestore mstore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grup_olustur, container, false);

        grupolusturgrupadieditText = view.findViewById(R.id.grupolustur_grupadieditText);
        grupolusturgrupaciklamaeditText = view.findViewById(R.id.grupolustur_grupaciklamaeditText);
        grupolusturgrupbutton = view.findViewById(R.id.grupolustur_grupbutton);
        grupolusturreyclerview = view.findViewById(R.id.grupolustur_reyclerview);
        grupolusturgrupsimgesi = view.findViewById(R.id.grupolustur_grupsimgesi);

        grupModelArrayList = new ArrayList<>();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),  result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        grupolusturgrupsimgesi.setImageURI(dosyayolu);
                    }
                });
                grupolusturgrupsimgesi.setOnClickListener(v ->{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                });

                grupolusturgrupbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String grupName = grupolusturgrupadieditText.getText().toString();
                        String grupDescription = grupolusturgrupaciklamaeditText.getText().toString();

                        if (grupName.isEmpty()){
                            return;
                        }
                        if(grupDescription.isEmpty()){
                            return;
                        }
                        if (dosyayolu != null){
                            StorageReference storageReference = mstorage.getReference().child("resimler/" + UUID.randomUUID().toString());
                            storageReference.putFile(dosyayolu).addOnSuccessListener(taskSnapshot -> {
                                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String dowloadUrl = uri.toString();

                                    Grupolustur(grupName, grupDescription, dowloadUrl);
                                });
                            });
                            return;
                        }
                        else{
                            Grupolustur(grupName, grupDescription, null);
                        }
                    }
                });
                FetchGrup();
        return view;
    }
    private void Grupolustur(String name, String description, String image){
        String userId = mAuth.getCurrentUser().getUid();

        mstore.collection("/userdata/*" + userId + "/" + "groups").add(new HashMap<String, Object>(){{
            put("name", name);
            put("description", description);
            put("image", image);
            put("numbers", new ArrayList<String>());
        }}).addOnSuccessListener(documentReference ->  {
            Toast.makeText(getContext(), "Grup Oluşturuldu.", Toast.LENGTH_SHORT).show();

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                GrupModel grupModel = new GrupModel(name, description, image, (List<String>) documentSnapshot.get("number"), documentSnapshot.getId());
                grupModelArrayList.add(grupModel);
                grupolusturreyclerview.getAdapter().notifyItemInserted(grupModelArrayList.size() - 1);
            });
        }).addOnSuccessListener(e -> {
            Toast.makeText(getContext(), "Grup Oluşturulamadı.", Toast.LENGTH_SHORT).show();
        });
    }
    private void FetchGrup(){
        String userId = mAuth.getCurrentUser().getUid();
        mstore.collection("/userdata/" + userId +  "/"+ "/groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            grupModelArrayList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                GrupModel grupModel = new GrupModel(documentSnapshot.getString("name"), documentSnapshot.getString("description"), documentSnapshot.getString("image"), (List<String>) documentSnapshot.get("number"), documentSnapshot.getId());
                grupModelArrayList.add(grupModel);
            }
            grupolusturreyclerview.setAdapter(new GrupAdaptor(grupModelArrayList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            grupolusturreyclerview.setLayoutManager(linearLayoutManager);
        });
    }
}




















