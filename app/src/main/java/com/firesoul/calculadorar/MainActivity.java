package com.firesoul.calculadorar;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {
    //Spinners
    Spinner SpnC1,SpnC2,SpnC3,SpnC4;
    //Boton
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //para poner icono en la action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.resistencia);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton = (Button)findViewById(R.id.Calcular);
        //En esta seccion llenamos los Spinners con los datos de los recursos.
        //Asociamos los controles del layput a nustras variables de control (spinner)
        SpnC1 = (Spinner)findViewById(R.id.SpnColor1);
        SpnC2 = (Spinner)findViewById(R.id.SpnColor2);
        SpnC3 = (Spinner)findViewById(R.id.SpnColor3);
        //Usamos el mismo adaptador para los primeros 3 ya que contienen los mismos datos.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Colores1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Asignamos el contenido del adaptador a nuestros controles
        SpnC1.setAdapter(adapter);
        SpnC2.setAdapter(adapter);
        SpnC3.setAdapter(adapter);
        //Hacemos lo mismo para el cuarto, pero aqui usamos el recurso "Colores2" que contiene los
        //datos para la cuarta franja, solo en este caso.
        SpnC4 = (Spinner)findViewById(R.id.SpnColor4);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Colores2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpnC4.setAdapter(adapter2);
        //Creamos el handler para el evento click
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Usamos la funcion ShowDialog para mostrar el resultado
                ShowDialog("El Resultado es:",BuildNumber(SpnC1.getSelectedItemPosition(),SpnC2.getSelectedItemPosition(),
                        SpnC3.getSelectedItemPosition())+" "+GetTolerance(SpnC4.getSelectedItemPosition()));
            }
        });
    }
    //Mostrar dialog de manera sencilla
    private void ShowDialog(String Title, String Caption)
    {
        new AlertDialog.Builder(this)
                .setTitle(Title)
                .setMessage(Caption)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
    //Funcion para obtener la tolerancia de la resistencia
    private String GetTolerance(int Value)
    {
        if (Value == 0)
            return "+5% de tolerancia";
        else
            return "+10% de tolerancia";
    }
    //Funcion para generar el valor de la resistencia basado en sus colores
    private String BuildNumber(int Value1, int Value2, int Value3)
    {
        String Significant;
        Significant = Integer.toString(Value1) + Integer.toString(Value2);
        double Resultado = Integer.parseInt(Significant)*pow(10,Value3);
        if (Resultado/1000 >= 1 && Resultado/1e3 < 1000 )
        {
            return (String.valueOf(Resultado / 1e3) + "KΩ");
        }
        //Para regresar el valor en MΩ
        if (Resultado/1e6 >= 1)
        {
            return (String.valueOf(Resultado / 1e6) + "MΩ");
        }else{
            //Regresamos el valor en Ω
            return (String.valueOf(Resultado)+"Ω");
        }

    }
}