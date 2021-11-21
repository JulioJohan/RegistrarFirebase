package mx.edu.utng.firebaseautentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Definiendo objetos
    private EditText TextEmail;
    private EditText TextContra;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    //Objeto Firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciar el objeto en firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //Referecia de los views

        TextEmail = (EditText) findViewById(R.id.TextEmail);
        TextContra = (EditText) findViewById(R.id.TextContra);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);
    }
    private void registrarUsuario(){
        //Se obtiene el email y contraseña desde las cajas del texto
        String email = TextEmail.getText().toString().trim();
        String contrasena = TextContra.getText().toString().trim();

        //verificacion de la caja de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingresa un email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(contrasena)){
            Toast.makeText(this,"Ingresa tu contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Realizando registro en linea");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"Se ha registrado exitosamente",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this,"No se registro exitosamente",Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        registrarUsuario();
    }
}
