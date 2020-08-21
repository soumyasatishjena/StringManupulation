package com.example.stringmanupulation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.stringmanupulation.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private ArrayList<String> recentData;
    private ArrayList<String> olderData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recentData = new ArrayList<>();
        olderData = new ArrayList<>();
        binding.buttonAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonAdd){
            getDataAndStore();
        }
    }

    private void getDataAndStore() {
        String dataEntered = binding.editText.getText().toString().trim().toLowerCase();
        if(!dataEntered.isEmpty()){
            if(!dataEntered.contains(" ")) {
                if (recentData.size() >= 2) {
                    boolean older = false;
                    boolean recent = false;
                    if (olderData.size() >= 3) {
                        for(int j = 0; j<olderData.size(); j++){
                            if(olderData.get(j).contains(dataEntered)){
                                older = true;
                                recentData.add(olderData.get(j));
                                olderData.remove(olderData.get(j));
                                olderData.add(recentData.get(0));
                                recentData.remove(0);
                                break;
                            }
                        }
                    }else {
                        for(int j = 0; j<olderData.size(); j++){
                            if(olderData.get(j).contains(dataEntered)){
                                older = true;
                                recentData.add(olderData.get(j));
                                olderData.remove(olderData.get(j));
                                olderData.add(recentData.get(0));
                                recentData.remove(0);
                                break;
                            }
                        }
                    }
                    if(!older && olderData.size()==3) {
                        olderData.remove(0);
                    }
                    for (int h = 0; h < recentData.size(); h++) {
                            if (recentData.get(h).contains(dataEntered)) {
                                recent = true;
                                binding.editText.setError("String already exist!!");
                                break;
                            }
                        }
                        if(!recent){
                            olderData.add(recentData.get(0));
                            recentData.remove(0);
                            recentData.add(dataEntered);
                        }
                }else {
                    boolean contain = false;
                    if(recentData.size()!= 0){
                     for(int i =0; i< recentData.size(); i++){
                         if(recentData.get(i).equals(dataEntered)) {
                             binding.editText.setError("String already exist!!");
                             contain = true;
                             break;
                         }
                     }
                     if(!contain){
                         recentData.add(dataEntered);
                     }
                    }else { recentData.add(dataEntered); }
                }
                setDataToView();
            }else
                binding.editText.setError("Cannot contain space..");
        }else
            binding.editText.setError("Enter Data to Enter..");
    }

    private void setDataToView(){
        StringBuilder builderOlder = new StringBuilder();
        for (String k : olderData) {
            builderOlder.append(k).append("\n");
        }
        binding.older.setText(builderOlder);
        StringBuilder builderRecent = new StringBuilder();
        for (String j : recentData) {
            builderRecent.append(j).append("\n");
        }
        binding.recent.setText(builderRecent);
        binding.editText.setText("");
        String l = "recent ="+ recentData.size() + ", " + "Older =" + olderData.size();
        Toast.makeText(this, l, Toast.LENGTH_SHORT).show();
    }
}