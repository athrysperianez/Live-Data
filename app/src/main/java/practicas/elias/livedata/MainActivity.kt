package practicas.elias.livedata

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var txtEdad: TextView
    lateinit var txtTrabajo: TextView
    lateinit var txtAprobar: TextView
    lateinit var swtTrabajo: Switch
    lateinit var swtAprobar: Switch
    lateinit var editTxtEdad: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtEdad = findViewById(R.id.txtEdad)
        txtTrabajo = findViewById(R.id.txtTrabajo)
        txtAprobar = findViewById(R.id.txtAprobar)
        swtAprobar = findViewById(R.id.switchAprobar)
        swtTrabajo = findViewById(R.id.switchTrabajo)
        editTxtEdad = findViewById(R.id.editTxtEdad)

        val observadorEdad = Observer<Int> { data ->
            if(data != null) {
                txtEdad.text = "Tienes " + data.toString() + " años"
            }
        }

        val observadorTrabajo = Observer<Boolean> { data ->
            if(data!=null){
                if(data){
                    txtTrabajo.text = "Trabajas en casa"
                }else{
                    txtTrabajo.text = "No trabajas en casa"
                }
            }
        }

        val observadorAprobar = Observer<Boolean> { data ->
            if(data!=null){
             if(data){
                 txtAprobar.text = "Vas a aprobar"
             }else{
                 txtAprobar.text = "No vas a aprobar"
             }
            }
        }

        val liveEdad: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }
        val liveAprobar: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }

        val liveTrabajo: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }

        liveTrabajo.observe(this, observadorTrabajo)
        liveAprobar.observe(this, observadorAprobar)
        liveEdad.observe(this, observadorEdad)

        editTxtEdad.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(datos: CharSequence?, p1: Int, p2: Int, p3: Int) {
                liveEdad.postValue(Integer.parseInt(datos.toString()))
            }
        })

        swtTrabajo.setOnCheckedChangeListener { _, isChecked ->
            liveTrabajo.postValue(isChecked)
        }

        swtAprobar.setOnCheckedChangeListener { _, isChecked ->
            liveAprobar.postValue(isChecked)
        }
    }
}
