package com.uylab.quiz.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uylab.quiz.R;
import com.uylab.quiz.databinding.ItemQuestionBinding;

import java.util.List;

public class Question_Adapter extends RecyclerView.Adapter<Question_Adapter.ViewHolder>{
    private List<Question> questionList;
    ItemQuestionBinding questionBinding;

    public Question_Adapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        questionBinding = DataBindingUtil.inflate(inflater, R.layout.item_question,parent,false);
        View view = questionBinding.getRoot();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        questionBinding.idNo.setText((position+1)+"");
        questionBinding.itemQuestion.setText(" "+questionList.get(position).getQuestionText());
        questionBinding.itemAText.setText(" "+questionList.get(position).getaText());
        questionBinding.itemBText.setText(" "+questionList.get(position).getbText());
        questionBinding.itemCText.setText(" "+questionList.get(position).getcText());
        questionBinding.itemDText.setText(" "+questionList.get(position).getdText());
        questionBinding.itemAnswerText.setText(" "+questionList.get(position).getCurrectAns());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
