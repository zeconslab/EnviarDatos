package mx.edu.itsz.proyectocurso;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String PATH_START = "start";
    private static final String PATH_MESSAGE = "message";
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btnSend)
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final TextView tvMessage = findViewById(R.id.tv_message);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference referencia = database.getReference(PATH_START).child(PATH_MESSAGE);

        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvMessage.setText(dataSnapshot.getValue(String.class));
                Toast.makeText(MainActivity.this, "Conexion Establecida", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error al consultar la base", Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {
        FirebaseDatabase basededatos = FirebaseDatabase.getInstance();
        final DatabaseReference referencia = basededatos.getReference(PATH_START).child(PATH_MESSAGE);

        referencia.setValue(etMessage.getText().toString().trim());
        etMessage.setText("");
    }
}
