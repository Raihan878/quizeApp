package com.uylab.quiz.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uylab.quiz.R;
import com.uylab.quiz.databinding.ActivityMainBinding;
import com.uylab.quiz.util.Question;
import com.uylab.quiz.util.Question_Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    FirebaseFirestore firestore;
    private List<Question> questionLists;
    private Question_Adapter questionAdapter;
    static final String collection_tbl1 = "tbl_Question";
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadNativeAd();
            }
        });
        intRecyclerView();
        loadNativeAd();
     //   mBinding.adView.setAdSize(new AdSize(300,50));
     //   mBinding.adView.setAdUnitId(getResources().getString(R.string.banner1));
        mBinding.adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
               // mBinding.adView.show
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mBinding.adView.loadAd(adRequest);
        mBinding.addbtnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String question = mBinding.questionID.getText().toString().trim();
                    String aOption = mBinding.optionAID.getText().toString().trim();
                    String bOption = mBinding.optionBID.getText().toString().trim();
                    String cOption = mBinding.optionCID.getText().toString().trim();
                    String dOption = mBinding.optionDID.getText().toString().trim();
                    String currectAns = mBinding.currectAns.getText().toString().trim();

                    String toastText = "Question : "+question+"\nA Option : "+aOption+"\nB Option : "+bOption+"\nC Option : "+cOption+"\nD Option : "+dOption+"\nCurrect Ans : "+currectAns;
                    Toast.makeText(MainActivity.this,toastText,Toast.LENGTH_SHORT).show();
                    insurtQuestion(question,aOption,bOption,cOption,dOption,currectAns);
                    clearAll();


            }
        });
    }

    private void loadNativeAd() {
        AdLoader adLoader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        //adLoader.loadAd(new AdRequest());
    }

    public void insurtQuestion(String question,String aOption,String bOption,String cOption,String dOption,String currectAns)
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Question",question);
        hashMap.put("AOption",aOption);
        hashMap.put("BOption",bOption);
        hashMap.put("COption",cOption);
        hashMap.put("DOption",dOption);
        hashMap.put("Current_Ans",currectAns);

        firestore.collection(collection_tbl1).add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        questionRealTimeListenar();
    }
    public void clearAll()
    {
        mBinding.questionID.setText("");
        mBinding.optionAID.setText("");
        mBinding.optionBID.setText("");
        mBinding.optionCID.setText("");
        mBinding.optionDID.setText("");
        mBinding.currectAns.setText("");
    }
    public void questionRealTimeListenar(){
        firestore.collection(collection_tbl1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<DocumentChange> documentChange = queryDocumentSnapshots.getDocumentChanges();
                for (DocumentChange dc : documentChange){
                    switch (dc.getType()){
                        case ADDED:
                            QueryDocumentSnapshot documentSnapshot = dc.getDocument();
                            Question question = new Question();
                            question.setId(documentSnapshot.getId());
                            question.setQuestionText(""+documentSnapshot.get("Question"));
                            question.setaText(""+documentSnapshot.get("AOption"));
                            question.setbText(""+documentSnapshot.get("BOption"));
                            question.setcText(""+documentSnapshot.get("COption"));
                            question.setdText(""+documentSnapshot.get("DOption"));
                            question.setCurrectAns(""+documentSnapshot.get("Current_Ans"));

                            questionLists.add(question);
                            questionAdapter.notifyDataSetChanged();
                            break;
                        /*case MODIFIED:
                            QueryDocumentSnapshot documentSnapshot_Modified = dc.getDocument();
                            Question question1 = new Question();
                            question1.setId(documentSnapshot_Modified.getId());
                            question1.setQuestionText(""+documentSnapshot_Modified.get("Question"));
                            question1.setaText(""+documentSnapshot_Modified.get("AOption"));
                            question1.setbText(""+documentSnapshot_Modified.get("BOption"));
                            question1.setcText(""+documentSnapshot_Modified.get("COption"));
                            question1.setdText(""+documentSnapshot_Modified.get("DOption"));
                            question1.setCurrectAns(""+documentSnapshot_Modified.get("Current_Ans"));
                            break;
                        case REMOVED:
                            QueryDocumentSnapshot documentSnapshot_Removed = dc.getDocument();
                            Question question2 = new Question();
                            question2.setId(documentSnapshot_Removed.getId());
                            question2.setQuestionText(""+documentSnapshot_Removed.get("Question"));
                            question2.setaText(""+documentSnapshot_Removed.get("AOption"));
                            question2.setbText(""+documentSnapshot_Removed.get("BOption"));
                            question2.setcText(""+documentSnapshot_Removed.get("COption"));
                            question2.setdText(""+documentSnapshot_Removed.get("DOption"));
                            question2.setCurrectAns(""+documentSnapshot_Removed.get("Current_Ans"));
                            break;*/

                        default:break;
                    }
                }
            }
        });
    }
    public void intRecyclerView(){
        questionLists = new ArrayList<>();
        questionAdapter = new Question_Adapter(questionLists);
        mBinding.recyclerViewID.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mBinding.recyclerViewID.setAdapter(questionAdapter);
    }

}
