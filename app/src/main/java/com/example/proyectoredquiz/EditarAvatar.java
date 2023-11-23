package com.example.proyectoredquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class EditarAvatar extends AppCompatActivity {

    Button btn_superior, btn_inferior, btn_color1, btn_color2, btn_color3, btn_color4, btn_volver, btn_guardar, btn_color1B, btn_color2B, btn_color3B;
    ImageView superior, inferior, avatar, zapatosH, superiorM, inferiorM, zapatosM;
    private int estado = 1;
    private FirebaseFirestore mfirestore;
    FirebaseAuth mAuth;
    private String idUser, generoUsuario;
    private int puntajeUsuario;
    private int prendaS = 1;
    private int prendaI = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_avatar);
        // AUTH
        mAuth = FirebaseAuth.getInstance();
        idUser = mAuth.getCurrentUser().getUid();
        //FIRESTORE
        mfirestore = FirebaseFirestore.getInstance();

        btn_guardar = findViewById(R.id.btn_guardarA);
        btn_volver = findViewById(R.id.btn_volverA2);
        btn_superior = findViewById(R.id.btn_sup);
        btn_inferior = findViewById(R.id.btn_inf);
        btn_color1 = findViewById(R.id.color1);
        btn_color2 = findViewById(R.id.color2);
        btn_color3 = findViewById(R.id.color3);
        btn_color4 = findViewById(R.id.color4);
        btn_color1B = findViewById(R.id.color1B);
        btn_color2B = findViewById(R.id.color2B);
        btn_color3B = findViewById(R.id.color3B);
        superior = findViewById(R.id.superiorHombre);
        inferior = findViewById(R.id.inferiorHombre);
        avatar = findViewById(R.id.avatar2);
        zapatosH = findViewById(R.id.imageView9);
        superiorM = findViewById(R.id.superiorM);
        inferiorM = findViewById(R.id.inferiorM);
        zapatosM = findViewById(R.id.zapatosM);

        //btn_color1.setVisibility(View.GONE);
        //btn_color2.setVisibility(View.GONE);
        //btn_color3.setVisibility(View.GONE);
        //btn_color4.setVisibility(View.GONE);

        btn_color1B.setVisibility(View.GONE);
        btn_color2B.setVisibility(View.GONE);
        btn_color3B.setVisibility(View.GONE);

        verificar();
        //verificar2();

        // OBTENER GENERO DEL USUARIO
        DocumentReference documentReference = mfirestore.collection("rqUsers").document(idUser);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Manejar el error, si es necesario
                    Log.e("ERROR", "Error al escuchar cambios en el documento", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Actualizar el valor de las vidas en el TextView
                    generoUsuario = documentSnapshot.getString("genero");
                    // EDITAR
                    if (estado == 1 && "Masculino".equals(generoUsuario)){
                        btn_color1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superior.setImageResource(R.drawable.basicahombreb);
                            }
                        });

                        btn_color2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superior.setImageResource(R.drawable.basicahombren);
                            }
                        });

                        btn_color3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superior.setImageResource(R.drawable.basicahombrev);
                            }
                        });

                        btn_color4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superior.setImageResource(R.drawable.basicahombrecr);
                            }
                        });
                    } else if (estado == 1 && "Femenino".equals(generoUsuario)){
                        btn_color1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superiorM.setImageResource(R.drawable.basicamujerb);
                            }
                        });

                        btn_color2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superiorM.setImageResource(R.drawable.basicamujeraz);
                            }
                        });

                        btn_color3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superiorM.setImageResource(R.drawable.basicamujerr);
                            }
                        });

                        btn_color4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                superiorM.setImageResource(R.drawable.basicamujercr);
                            }
                        });
                    }
                    Log.e("EtiquetaDeRegistro", "Valor del género del usuario: " + generoUsuario);
                    if ("Masculino".equals(generoUsuario)) {
                        avatar.setImageResource(R.drawable.hombre);
                        superior.setVisibility(View.VISIBLE);
                        inferior.setVisibility(View.VISIBLE);
                        zapatosH.setVisibility(View.VISIBLE);
                        superiorM.setVisibility(View.GONE);
                        inferiorM.setVisibility(View.GONE);
                        zapatosM.setVisibility(View.GONE);
                    } else if ("Femenino".equals(generoUsuario)) {
                        avatar.setImageResource(R.drawable.mujer);
                        btn_color1.setBackgroundColor(ContextCompat.getColor(EditarAvatar.this, R.color.blancoH));
                        btn_color2.setBackgroundColor(ContextCompat.getColor(EditarAvatar.this, R.color.azulM));
                        btn_color3.setBackgroundColor(ContextCompat.getColor(EditarAvatar.this, R.color.rosaM));
                        btn_color4.setBackgroundColor(ContextCompat.getColor(EditarAvatar.this, R.color.cruzRoja));
                        superior.setVisibility(View.GONE);
                        inferior.setVisibility(View.GONE);
                        zapatosH.setVisibility(View.GONE);
                        superiorM.setVisibility(View.VISIBLE);
                        inferiorM.setVisibility(View.VISIBLE);
                        zapatosM.setVisibility(View.VISIBLE);

                    }

                    puntajeUsuario = documentSnapshot.getLong("puntaje").intValue();
                    //puntaje.setText(String.valueOf(puntajeUsuario));
                    //vidas.setText("X " + String.valueOf(VIDAS));
                }
            }
        });

        // VOLVER
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent index = new Intent(EditarAvatar.this, miavatar.class);
                startActivities(new Intent[]{index});
            }
        });

        //Log.d("DEBUG", "Estado: " + estado + ", Género: " + generoUsuario);
        // SUPERIOR ACTIVADO
        btn_superior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Masculino".equals(generoUsuario)) {
                    estado = 1;
                    //verificar();
                    //verificar2();
                    botonActual1();
                }
                if ("Femenino".equals(generoUsuario)){
                    estado = 1;
                    //verificar();
                    //verificar2();
                    botonActualM1();
                }
            }
        });

        // INFERIOR ACTIVADO
        btn_inferior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Masculino".equals(generoUsuario)) {
                    estado = 2;
                    //verificar();
                    //verificar2();
                    botonActual2();
                }
                if ("Femenino".equals(generoUsuario)){
                    estado = 2;
                    //verificar();
                    //verificar2();
                    botonActualM2();
                }
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama al método para guardar prendas en Firestore
                guardarPrendasFirestore();
                finish();
                Intent index = new Intent(EditarAvatar.this, miavatar.class);
                startActivities(new Intent[]{index});
            }
        });

    }

    // HOMBRE
    private void botonActual1() {
        btn_superior.setBackgroundColor(ContextCompat.getColor(this, R.color.actual));
        btn_superior.setTextColor(ContextCompat.getColor(this, R.color.white));
        btn_inferior.setBackgroundColor(ContextCompat.getColor(this, R.color.pendiente));
        btn_inferior.setTextColor(ContextCompat.getColor(this, R.color.black));

        btn_color1.setBackgroundColor(ContextCompat.getColor(this, R.color.blancoH));
        btn_color2.setBackgroundColor(ContextCompat.getColor(this, R.color.naranjaH));
        btn_color3.setBackgroundColor(ContextCompat.getColor(this, R.color.verdeH));
        btn_color4.setBackgroundColor(ContextCompat.getColor(this, R.color.cruzRoja));

        btn_color1.setVisibility(View.VISIBLE);

        verificar();

        btn_color1B.setVisibility(View.GONE);
        btn_color2B.setVisibility(View.GONE);
        btn_color3B.setVisibility(View.GONE);

        //btn_color4.setVisibility(View.VISIBLE);

        if (estado == 1){
            btn_color1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 1;
                    superior.setImageResource(R.drawable.basicahombreb);
                }
            });

            btn_color2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 2;
                    superior.setImageResource(R.drawable.basicahombren);
                }
            });

            btn_color3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 3;
                    superior.setImageResource(R.drawable.basicahombrev);
                }
            });

            btn_color4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 4;
                    superior.setImageResource(R.drawable.basicahombrecr);
                }
            });
        }
    }

    private void botonActual2() {
        btn_superior.setBackgroundColor(ContextCompat.getColor(this, R.color.pendiente));
        btn_superior.setTextColor(ContextCompat.getColor(this, R.color.black));
        btn_inferior.setBackgroundColor(ContextCompat.getColor(this, R.color.actual));
        btn_inferior.setTextColor(ContextCompat.getColor(this, R.color.white));

        btn_color1B.setBackgroundColor(ContextCompat.getColor(this, R.color.pantalonH1));
        btn_color2B.setBackgroundColor(ContextCompat.getColor(this, R.color.pantalonH2));
        btn_color3B.setBackgroundColor(ContextCompat.getColor(this, R.color.pantalonH3));

        btn_color1B.setVisibility(View.VISIBLE);

        verificar2();

        btn_color1.setVisibility(View.GONE);
        btn_color2.setVisibility(View.GONE);
        btn_color3.setVisibility(View.GONE);
        btn_color4.setVisibility(View.GONE);

        if (estado == 2){
            btn_color1B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaI = 1;
                    inferior.setImageResource(R.drawable.pantalonhombrea);
                }
            });

            btn_color2B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaI = 2;
                    inferior.setImageResource(R.drawable.pantalonhombreac);
                }
            });

            btn_color3B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaI = 3;
                    inferior.setImageResource(R.drawable.pantalonhombreg);
                }
            });

        }
    }

    // MUJER
    private void botonActualM1() {
        btn_superior.setBackgroundColor(ContextCompat.getColor(this, R.color.actual));
        btn_superior.setTextColor(ContextCompat.getColor(this, R.color.white));
        btn_inferior.setBackgroundColor(ContextCompat.getColor(this, R.color.pendiente));
        btn_inferior.setTextColor(ContextCompat.getColor(this, R.color.black));

        verificar();

        btn_color1.setBackgroundColor(ContextCompat.getColor(this, R.color.blancoH));
        btn_color2.setBackgroundColor(ContextCompat.getColor(this, R.color.azulM));
        btn_color3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosaM));
        btn_color4.setBackgroundColor(ContextCompat.getColor(this, R.color.cruzRoja));
        //btn_color4.setVisibility(View.VISIBLE);

        btn_color1.setVisibility(View.VISIBLE);

        btn_color1B.setVisibility(View.GONE);
        btn_color2B.setVisibility(View.GONE);
        btn_color3B.setVisibility(View.GONE);

        if (estado == 1){
            btn_color1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 1;
                    superiorM.setImageResource(R.drawable.basicamujerb);
                }
            });

            btn_color2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 2;
                    superiorM.setImageResource(R.drawable.basicamujeraz);
                }
            });

            btn_color3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 3;
                    superiorM.setImageResource(R.drawable.basicamujerr);
                }
            });

            btn_color4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaS = 4;
                    superiorM.setImageResource(R.drawable.basicamujercr);
                }
            });
        }
    }

    private void botonActualM2() {
        btn_superior.setBackgroundColor(ContextCompat.getColor(this, R.color.pendiente));
        btn_superior.setTextColor(ContextCompat.getColor(this, R.color.black));
        btn_inferior.setBackgroundColor(ContextCompat.getColor(this, R.color.actual));
        btn_inferior.setTextColor(ContextCompat.getColor(this, R.color.white));

        btn_color1B.setBackgroundColor(ContextCompat.getColor(this, R.color.pantalonH1));
        btn_color2B.setBackgroundColor(ContextCompat.getColor(this, R.color.pantalonH4));
        btn_color3B.setBackgroundColor(ContextCompat.getColor(this, R.color.pantalonH3));

        btn_color1B.setVisibility(View.VISIBLE);

        verificar2();

        btn_color1.setVisibility(View.GONE);
        btn_color2.setVisibility(View.GONE);
        btn_color3.setVisibility(View.GONE);
        btn_color4.setVisibility(View.GONE);

        if (estado == 2){
            btn_color1B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaI = 1;
                    inferiorM.setImageResource(R.drawable.pantalonmujera);
                }
            });

            btn_color2B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaI = 2;
                    inferiorM.setImageResource(R.drawable.pantalonmujerc);
                }
            });

            btn_color3B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prendaI = 3;
                    inferiorM.setImageResource(R.drawable.pantalonmujerg);
                }
            });

        }
    }

    private CompletableFuture<Boolean> obtenerNombreRecompensa1(String userId) {
        // Construir la referencia al documento de recompensas del usuario en Firestore
        String recompensaDocumentPath = "rqRecompensas/" + userId;

        // Crear un CompletableFuture para manejar el resultado
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        // Obtener el documento de recompensas del usuario
        mfirestore.document(recompensaDocumentPath)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("recompensa1")) {
                        // Obtener el valor del atributo "recompensa5"
                        boolean recompensa5 = documentSnapshot.getBoolean("recompensa1");

                        // Resuelve el CompletableFuture con el valor de recompensa5
                        future.complete(recompensa5);
                    } else {
                        // Si no existe el atributo "recompensa5", resuelve con false
                        future.complete(false);
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento de recompensas del usuario
                    Toast.makeText(EditarAvatar.this, "Error al obtener el documento de recompensas desde Firestore", Toast.LENGTH_SHORT).show();
                    // Resuelve el CompletableFuture con un valor por defecto (puedes ajustarlo según tus necesidades)
                    future.complete(false);
                });

        // Devuelve el CompletableFuture
        return future;
    }

    private CompletableFuture<Boolean> obtenerNombreRecompensa2(String userId) {
        // Construir la referencia al documento de recompensas del usuario en Firestore
        String recompensaDocumentPath = "rqRecompensas/" + userId;

        // Crear un CompletableFuture para manejar el resultado
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        // Obtener el documento de recompensas del usuario
        mfirestore.document(recompensaDocumentPath)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("recompensa2")) {
                        // Obtener el valor del atributo "recompensa5"
                        boolean recompensa5 = documentSnapshot.getBoolean("recompensa2");

                        // Resuelve el CompletableFuture con el valor de recompensa5
                        future.complete(recompensa5);
                    } else {
                        // Si no existe el atributo "recompensa5", resuelve con false
                        future.complete(false);
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento de recompensas del usuario
                    Toast.makeText(EditarAvatar.this, "Error al obtener el documento de recompensas desde Firestore", Toast.LENGTH_SHORT).show();
                    // Resuelve el CompletableFuture con un valor por defecto (puedes ajustarlo según tus necesidades)
                    future.complete(false);
                });

        // Devuelve el CompletableFuture
        return future;
    }

    private CompletableFuture<Boolean> obtenerNombreRecompensa3(String userId) {
        // Construir la referencia al documento de recompensas del usuario en Firestore
        String recompensaDocumentPath = "rqRecompensas/" + userId;

        // Crear un CompletableFuture para manejar el resultado
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        // Obtener el documento de recompensas del usuario
        mfirestore.document(recompensaDocumentPath)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("recompensa3")) {
                        // Obtener el valor del atributo "recompensa5"
                        boolean recompensa5 = documentSnapshot.getBoolean("recompensa3");

                        // Resuelve el CompletableFuture con el valor de recompensa5
                        future.complete(recompensa5);
                    } else {
                        // Si no existe el atributo "recompensa5", resuelve con false
                        future.complete(false);
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento de recompensas del usuario
                    Toast.makeText(EditarAvatar.this, "Error al obtener el documento de recompensas desde Firestore", Toast.LENGTH_SHORT).show();
                    // Resuelve el CompletableFuture con un valor por defecto (puedes ajustarlo según tus necesidades)
                    future.complete(false);
                });

        // Devuelve el CompletableFuture
        return future;
    }

    private CompletableFuture<Boolean> obtenerNombreRecompensa4(String userId) {
        // Construir la referencia al documento de recompensas del usuario en Firestore
        String recompensaDocumentPath = "rqRecompensas/" + userId;

        // Crear un CompletableFuture para manejar el resultado
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        // Obtener el documento de recompensas del usuario
        mfirestore.document(recompensaDocumentPath)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("recompensa4")) {
                        // Obtener el valor del atributo "recompensa5"
                        boolean recompensa5 = documentSnapshot.getBoolean("recompensa4");

                        // Resuelve el CompletableFuture con el valor de recompensa5
                        future.complete(recompensa5);
                    } else {
                        // Si no existe el atributo "recompensa5", resuelve con false
                        future.complete(false);
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento de recompensas del usuario
                    Toast.makeText(EditarAvatar.this, "Error al obtener el documento de recompensas desde Firestore", Toast.LENGTH_SHORT).show();
                    // Resuelve el CompletableFuture con un valor por defecto (puedes ajustarlo según tus necesidades)
                    future.complete(false);
                });

        // Devuelve el CompletableFuture
        return future;
    }

    private CompletableFuture<Boolean> obtenerNombreRecompensa5(String userId) {
        // Construir la referencia al documento de recompensas del usuario en Firestore
        String recompensaDocumentPath = "rqRecompensas/" + userId;

        // Crear un CompletableFuture para manejar el resultado
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        // Obtener el documento de recompensas del usuario
        mfirestore.document(recompensaDocumentPath)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("recompensa5")) {
                        // Obtener el valor del atributo "recompensa5"
                        boolean recompensa5 = documentSnapshot.getBoolean("recompensa5");

                        // Resuelve el CompletableFuture con el valor de recompensa5
                        future.complete(recompensa5);
                    } else {
                        // Si no existe el atributo "recompensa5", resuelve con false
                        future.complete(false);
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento de recompensas del usuario
                    Toast.makeText(EditarAvatar.this, "Error al obtener el documento de recompensas desde Firestore", Toast.LENGTH_SHORT).show();
                    // Resuelve el CompletableFuture con un valor por defecto (puedes ajustarlo según tus necesidades)
                    future.complete(false);
                });

        // Devuelve el CompletableFuture
        return future;
    }

    private void verificar(){
        //btn_color2.setVisibility(View.GONE);
        obtenerNombreRecompensa1(idUser).thenAccept(result -> {
            // Hacer algo con el resultado (result) dentro del bloque thenAccept
            if (result) {
                // La recompensa5 está desbloqueada
                // Realiza las acciones necesarias aquí
                btn_color2.setVisibility(View.VISIBLE);
            } else {
                // La recompensa5 no está desbloqueada
                // Realiza otras acciones aquí
                btn_color2.setVisibility(View.GONE);
            }
        });

        obtenerNombreRecompensa3(idUser).thenAccept(result -> {
            // Hacer algo con el resultado (result) dentro del bloque thenAccept
            if (result) {
                // La recompensa5 está desbloqueada
                // Realiza las acciones necesarias aquí
                btn_color3.setVisibility(View.VISIBLE);
            } else {
                // La recompensa5 no está desbloqueada
                // Realiza otras acciones aquí
                btn_color3.setVisibility(View.GONE);
            }
        });

        obtenerNombreRecompensa5(idUser).thenAccept(result -> {
            // Hacer algo con el resultado (result) dentro del bloque thenAccept
            if (result) {
                // La recompensa5 está desbloqueada
                // Realiza las acciones necesarias aquí
                btn_color4.setVisibility(View.VISIBLE);
            } else {
                // La recompensa5 no está desbloqueada
                // Realiza otras acciones aquí
                btn_color4.setVisibility(View.GONE);
            }
        });
    }

    private void verificar2(){
        obtenerNombreRecompensa2(idUser).thenAccept(result -> {
            // Hacer algo con el resultado (result) dentro del bloque thenAccept
            if (result) {
                // La recompensa5 está desbloqueada
                // Realiza las acciones necesarias aquí
                btn_color2B.setVisibility(View.VISIBLE);
            } else {
                // La recompensa5 no está desbloqueada
                // Realiza otras acciones aquí
                btn_color2B.setVisibility(View.GONE);
            }
        });
        /*else{
            btn_color2B.setVisibility(View.GONE);
            {*/
        obtenerNombreRecompensa4(idUser).thenAccept(result -> {
            // Hacer algo con el resultado (result) dentro del bloque thenAccept
            if (result) {
                // La recompensa5 está desbloqueada
                // Realiza las acciones necesarias aquí
                btn_color3B.setVisibility(View.VISIBLE);
            } else {
                // La recompensa5 no está desbloqueada
                // Realiza otras acciones aquí
                btn_color3B.setVisibility(View.GONE);
            }
        });
    }

    private void guardarPrendasFirestore() {
        // Crear un mapa con los datos que deseas guardar
        Map<String, Object> prendas = new HashMap<>();
        prendas.put("prendaI", prendaI);
        prendas.put("prendaS", prendaS);

        // Construir la referencia al documento del usuario en Firestore
        DocumentReference userDocument = mfirestore.collection("rqUsers").document(idUser);

        // Actualizar el documento con los nuevos valores
        userDocument.set(prendas, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditarAvatar.this, "Avatar guardado exitosamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditarAvatar.this, "Error al guardar prendas: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}