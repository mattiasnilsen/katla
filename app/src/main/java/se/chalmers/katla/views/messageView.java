//package se.chalmers.katla.views;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.GestureDetector;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.util.List;
//
//import se.chalmers.katla.R;
//
////TODO: Swype rörelser samt plocka all information från modellens singleton.
//
//public class messageView extends Activity implements GestureDetector.OnGestureListener{
//    private TextView myText = null;
//    private String textMsg = "My text";
//    GestureDetector detector = new GestureDetector(this);
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message_view2);
//        LinearLayout lView = new LinearLayout(this);
//
//        myText = new TextView(this);
//        myText.setTextSize(100);
//        myText.setText(textMsg);
//
//        lView.addView(myText);
//
//        setContentView(lView);
//    }
//
//<<<<<<< HEAD:app/src/main/java/se/chalmers/katla/views/messageView.java
//    private void previousMessage(){
//        myText.setText("Modellen än inte skapad");//Model.getConversation.getPreviousMessage());
//=======
//    /*private void previousMessage(){
//        myText.setText(Model.getConversation.getPreviousMessage());
//>>>>>>> contactService:app/src/main/java/se/chalmers/katla/messageView.java
//    }
//    private void nextMessage(){
//        myText.setText("Modellen är än inte skapad ordenltigt");//Model.getConversation.getNextMessage());
//    }
//*/
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.message_view, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void setTextMessage(String string){
//        textMsg=string;
//    }
//
//    public String getTextMessage(){
//        return textMsg;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return detector.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean onDown(MotionEvent motionEvent) {
//        //previousMessage();
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent motionEvent) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent motionEvent) {
//        //nextMessage();
//    }
//
//    @Override
//    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
//        return false;
//    }
//}
