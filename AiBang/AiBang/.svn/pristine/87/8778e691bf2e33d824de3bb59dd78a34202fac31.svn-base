package edu.jxsd.x3510.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class PictureView extends View{
	 public Bitmap bitmap1=null; 
	    public Bitmap bitmap2=null; 
	    public int myInterval=10; 

	public PictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int myViewWidth=getWidth()-myInterval*2; 
        int myViewHeight=getHeight()-myInterval; 
        int lessLen=myViewWidth/2<myViewHeight?myViewWidth/2:myViewHeight; 
        /*»­Î»Í¼1*/ 
        if (bitmap1!=null) { 
            RectF dst1=new RectF(0,myInterval,lessLen,lessLen+myInterval); 
            canvas.drawBitmap(bitmap1, null, dst1, null); 
       } 
        /*»­Î»Í¼2*/ 
        if(bitmap2!=null){ 
            RectF dst2=new RectF(lessLen+myInterval*2,myInterval,lessLen*2+myInterval*2,lessLen+myInterval); 
            canvas.drawBitmap(bitmap2, null, dst2, null); 
        } 
   }   
	

}
