package com.example.proyecto_facturas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_facturas.data.rom.Factura
import com.example.proyecto_facturas.data.rom.FacturaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrearFacturaActivity : AppCompatActivity() {

    private lateinit var etDescEstado: EditText
    private lateinit var etImporteOrdenacion: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnGuardarFactura: Button
    private lateinit var db: FacturaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_factura)

        etDescEstado = findViewById(R.id.etDescEstado)
        etImporteOrdenacion = findViewById(R.id.etImporteOrdenacion)
        etFecha = findViewById(R.id.etFecha)
        btnGuardarFactura = findViewById(R.id.btnGuardarFactura)
        db = FacturaDatabase.getAppDBInstance(this)

        btnGuardarFactura.setOnClickListener {
            val descEstado = etDescEstado.text.toString()
            val importeOrdenacion = etImporteOrdenacion.text.toString().toDoubleOrNull()
            val fecha = etFecha.text.toString()

            if (descEstado.isNotEmpty() && importeOrdenacion != null && fecha.isNotEmpty()) {
                val nuevaFactura = Factura(
                    descEstado = descEstado,
                    importeOrdenacion = importeOrdenacion,
                    fecha = fecha
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        db.getAppDAO().insertarFactura(nuevaFactura)
                        runOnUiThread {
                            Toast.makeText(this@CrearFacturaActivity, "Factura guardada", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@CrearFacturaActivity, "Error al guardar la factura", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}