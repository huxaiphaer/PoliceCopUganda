package company.override.huzykamz.policecopuganda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

import adapter.MissingCitizenAdapter;
import adapter.NationalIdAdapter;
import adapter.PermitVerifyAdapter;
import adapter.VehicleChargesAdapter;
import adapter.VehicleVerificationAdapter;
import model.MissingCitizen;
import model.NationalID_Model;
import model.PermitVerifyModel;
import model.VehicleChargesModel;
import model.VehicleRegistrationModel;


/**
 * Created by HUZY_KAMZ on 2/11/2017.
 */

public class VehicleChargesFragment extends Fragment {

    // ProgressBar pd;
    private RecyclerView rv;
    private EditText vehicle_verify_edt;
    private Button search_track_bt;
    public VehicleChargesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_video_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.vehicle_charges_fragment, container, false);

        rv = (RecyclerView) v.findViewById(R.id.rv_charges_verify);
        // pd = (ProgressBar) v.findViewById(R.id.progressBar_citizen);
        search_track_bt =(Button)v.findViewById(R.id.charges_verify_bt);
        vehicle_verify_edt =(EditText)v.findViewById(R.id.charges_verify_edt);
        rv.hasFixedSize();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        search_track_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadData();
            }
        });


        return v;
    }







    public void LoadData(){

        String TextInserted= vehicle_verify_edt.getText().toString();
        // 192.168.43.104
        final String url = "http://192.168.43.104:8092/PoliceApp/CheckCharges.aspx?DataFormat=PlateNo&PlateNo="+TextInserted;

        final  ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Fetching Data ... ");
        pd.show();



        Ion.with(VehicleChargesFragment.this)
                .load(url)
                .progressDialog(pd)
                .as(new TypeToken<List<VehicleChargesModel>>() {
                })
                .setCallback(new FutureCallback<List<VehicleChargesModel>>() {

                    @Override
                    public void onCompleted(Exception e, List<VehicleChargesModel> itemList) {
                        try {

                            if(itemList!=null) {
                                final VehicleChargesAdapter adapter = new VehicleChargesAdapter(itemList, getContext());


                                rv.setAdapter(adapter);
                                rv.hasFixedSize();
                                rv.setLayoutManager(new LinearLayoutManager(getContext()));


                                pd.dismiss();
                            }
                            else  if (itemList.equals("")) {
                                Toast.makeText(getContext(), "This Plate Number is not Valid ", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                            else {
                                Toast.makeText(getContext(), "This Plate Number is not Valid ", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }
                        catch (Exception ex){
                            Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });




    }




}
