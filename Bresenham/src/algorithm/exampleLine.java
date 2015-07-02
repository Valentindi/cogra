package algorithm;

import javafx.scene.paint.Color;

public class exampleLine {

	public static Color[][] run(int beginX, int beginY, int endX, int endY, Boolean changeX, Boolean changeY){
		System.out.println("Noch nicht implementiert");
		return null;
	}
//	public Color[][] dummyRectGreyScale;

	//public  Color[][] run(int beginX, int beginY, int endX, int endY, Boolean changeX, Boolean changeY) {
	//	dummyRectGreyScale = new Color[(int) (endX - beginX)][(int) (endY - beginY)];
		
		/*procedure bres1(x0,y0,xn,dx,dy:integer;sp:boolean);
var sw,d,d1,d2,x,y:integer; var sw,d,d1,d2,x,y:integer;
begin
if dy<0 then begin sw:=-1; dy:=-dy end else sw:=1;
d:=2*dy-dx; d1:=2*dy; d2:=2*(dy-dx); 
x:=x0; 
y:=y0; 
d:=2*dy-dx; 
d1:=2*dy; 
d2:=2*(dy-dx); 
x:=x0; 
y:=y0;
if not sp then putpixel(x,y,f) else putpixel(y,x,f);
while x<xn do
begin begin
x:=x+1;
if d<0 then d:=d+d1
else begin y:=y+sw; d:=d+d2 end; 
if not sp then putpixel(x,y,f) else putpixel(y,x,f)
end;
end;

*procedure bresline(x0,y0,xn,yn:integer;f:TColor); 
*procedure bresline(x0,y0,xn,yn:integer;f:TColor);
var dx,dy:integer;
begin
dx:=xn-x0;dy:=yn-y0;
if abs(dx)>=abs(dy) then {Anstieg -45 .. 0 .. +45 } if abs(dx)>=abs(dy) then {Anstieg  45 .. 0 .. +45 }
if x0>xn then bresline(xn,yn,x0,y0,f) else  
bres1(x0,y0,xn,dx,dy,false)
else {Anstieg +45 .. 90 .. -45} else {Anstieg +45 .. 90 ..  45}
if y0>yn then bresline(xn,yn,x0,y0,f) else 
bres1(y0,x0,yn,dy,dx,true);
end; end;

/
	int sw, d, d1, d2, x, y , xn,dx, dy;
	Boolean sp;
	
	dx = endX - beginX;
	dy = endY - beginY;
	System.out.println("DX: " + dx +" dy: " + dy);
	
	if(dy<0){
		sw = -1;
		dy = - dy;
		sp = true;
	}else{
		sw = 1;
		sp = false;
	}
	
	d= 2* dy-dx; 
	d1 = 2*dy;
	d2= 2*(dy-dx);
	x = 0;
	y = 0;
	xn = endX - beginX;
	sp = false;
	
	System.out.println(d + ": " + d1 + " : " + d2 + " : " + x + " : " + y + " : " + xn + " sp " + sp);
	/*
	if(!sp){
		dummyRectGreyScale = putPixel(x,y, dummyRectGreyScale);
	}else{
		dummyRectGreyScale =putPixel(y,x, dummyRectGreyScale);
	}
	
	System.out.println("Run Example Line from " +  beginX + " : " +  beginY + " to " + 
	endX + " : " +  endY + " with: " + d1 + " : " + d2);
	while (x < xn){
		System.out.println("D: " + d );
		if(d<0){
			d = d+ d1;
		}else{
			y= y + sw;
			d = d + d2;
		}
		if(!sp){
			dummyRectGreyScale = putPixel(x, y, dummyRectGreyScale);
		}else{
			dummyRectGreyScale = putPixel(y,x, dummyRectGreyScale);
		}
		x++;

		
	}
	
	

		
		return dummyRectGreyScale;
	}

	private Color[][] putPixel(int x, int y, Color[][] dummyRectGreyScale2) {
		System.out.println("Set Pixel at " + x + " : " + y);
		if(x<dummyRectGreyScale2.length && y<dummyRectGreyScale2[0].length){
		dummyRectGreyScale2[x][y] = Color.BLACK;
		}
		return dummyRectGreyScale2;
	}



}

/*

double dx = (double) endX-(double)beginX;
double dy = (double) endY-(double)beginY;

int x= 0;
int y = 0;
double fehler = dx/2; 


if((changeX || changeY) == true && (changeX && changeY)==false ){
	System.out.println("Andere Richtung");
	
	
	
}

while( x<(endX-beginX) && y < (endY - beginY)){
	dummyRectGreyScale[x][y]=Color.BLACK;

	x++;
	fehler = fehler - dy;
	if( fehler < 0){
		y++;
		fehler = fehler + dx;
	}
	System.out.println(x + " : " + y +  " von " + dx + " : " + dy);
}



/*while ((i<(endX-beginX))&&(j<(endY-beginY))) {
	dummyRectGreyScale[(int)i-1][(int)j-1]=GreyScaleFactory.getGreyScale(1-(slope-currentSlope));
	System.out.println(1-(slope-currentSlope));
//	System.out.println((dummyRectGreyScale[i-1][j-1]).toString());
	System.out.println(i +" : " + j);
	
	if(currentSlope < slope){
		i++;
	}else{
		j++;
	}
	currentSlope = i/j;
	System.out.println("Current Slope: " +  currentSlope);
		
}*/
}