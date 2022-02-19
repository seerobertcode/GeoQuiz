Geo Quiz App

# How to set position(gravity to toast)
```
Toast myToast = Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT);
myToast.setGravity(Gravity.TOP, 0, 250);
myToast.show();
```