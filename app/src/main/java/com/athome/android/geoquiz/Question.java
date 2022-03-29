package com.athome.android.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsQuestionAnswered;

    public Question(int mTextResId, boolean mAnswerTrue, boolean mIsQuestionAnswered) {
        this.mTextResId = mTextResId;
        this.mAnswerTrue = mAnswerTrue;
        this.mIsQuestionAnswered = mIsQuestionAnswered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isQuestionAnswered() {
        return mIsQuestionAnswered;
    }

    public void setQuestionAnswered(boolean questionAnswered) {
        mIsQuestionAnswered = questionAnswered;
    }
}
