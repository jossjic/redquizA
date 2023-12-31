package com.example.proyectoredquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdatePregunta extends AppCompatActivity {

    private Spinner spinnerCategory;
    private ArrayAdapter<CharSequence> categoryAdapter;
    Button btn_update, btn_back;
    EditText question, rate, correct, incorrect1, incorrect2, incorrect3;
    private FirebaseFirestore mFirestore;
    private String questionId; // Variable para almacenar el ID de la pregunta que se va a actualizar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pregunta);

        // FIRESTORE
        mFirestore = FirebaseFirestore.getInstance();

        // Obtener el ID de la pregunta de la actividad anterior
        Intent intent = getIntent();
        questionId = intent.getStringExtra("PREGUNTA_ID");

        // SPINNER
        spinnerCategory = findViewById(R.id.categoria3);
        categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categrias_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // INSTANCIAS
        btn_back = findViewById(R.id.btn_back2);
        btn_update = findViewById(R.id.btn_agregar2);
        question = findViewById(R.id.pregunta3);
        rate = findViewById(R.id.rating3);
        correct = findViewById(R.id.correcta3);
        incorrect1 = findViewById(R.id.incorrecta6);
        incorrect2 = findViewById(R.id.incorrecta7);
        incorrect3 = findViewById(R.id.incorrecta8);

        // Obtener los datos de la pregunta existente y mostrarlos en los campos de entrada
        obtenerDatosPregunta();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdatePregunta.this, VerReactivos.class);
                startActivity(intent);
                finish();
            }
        });

        // ACTUALIZAR
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String preguntai = question.getText().toString();
                String puntajei = rate.getText().toString();
                String correctai = correct.getText().toString();
                String incorrecta1i = incorrect1.getText().toString();
                String incorrecta2i = incorrect2.getText().toString();
                String incorrecta3i = incorrect3.getText().toString();
                String categoriai = spinnerCategory.getSelectedItem().toString();

                if (preguntai.isEmpty() || puntajei.isEmpty() || correctai.isEmpty() || incorrecta1i.isEmpty() || incorrecta2i.isEmpty() || incorrecta3i.isEmpty()) {
                    Toast.makeText(UpdatePregunta.this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    actualizarPregunta(questionId, preguntai, puntajei, correctai, incorrecta1i, incorrecta2i, incorrecta3i, categoriai);
                }
            }
        });
    }

    private void obtenerDatosPregunta() {
        mFirestore.collection("preguntas").document(questionId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Obtener datos de la pregunta existente
                            String pregunta = documentSnapshot.getString("pregunta");
                            String categoria = documentSnapshot.getString("categoria");
                            String puntos = documentSnapshot.getString("puntos");
                            String correcta = documentSnapshot.getString("correcta");
                            String incorrecta1 = documentSnapshot.getString("incorrecta1");
                            String incorrecta2 = documentSnapshot.getString("incorrecta2");
                            String incorrecta3 = documentSnapshot.getString("incorrecta3");

                            // Mostrar datos en los campos de entrada
                            question.setText(pregunta);
                            rate.setText(puntos);
                            correct.setText(correcta);
                            incorrect1.setText(incorrecta1);
                            incorrect2.setText(incorrecta2);
                            incorrect3.setText(incorrecta3);

                            // Seleccionar la categoría en el Spinner
                            int index = categoryAdapter.getPosition(categoria);
                            spinnerCategory.setSelection(index);
                        } else {
                            Toast.makeText(UpdatePregunta.this, "La pregunta no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdatePregunta.this, "Error al obtener datos de la pregunta", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarPregunta(String questionId, String preguntai, String puntajei, String correctai, String incorrecta1i, String incorrecta2i, String incorrecta3i, String categoriai) {
        // Utiliza el ID de la pregunta para actualizar el documento existente en lugar de agregar uno nuevo.
        Map<String, Object> map = new HashMap<>();
        map.put("pregunta", preguntai);
        map.put("categoria", categoriai);
        map.put("puntos", puntajei);
        map.put("correcta", correctai);
        map.put("incorrecta1", incorrecta1i);
        map.put("incorrecta2", incorrecta2i);
        map.put("incorrecta3", incorrecta3i);

        mFirestore.collection("preguntas").document(questionId).update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdatePregunta.this, "Pregunta actualizada exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdatePregunta.this, "Error al actualizar la pregunta", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}