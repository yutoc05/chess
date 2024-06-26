import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class WKnight extends WhitePiece
{
    public void act()
    {
        if (!dragging()) {
            int moveType = moveType();
            if (((Math.abs(currentX - getX()) == 1) && (Math.abs(currentY - getY()) == 2)) || ((Math.abs(currentX - getX()) == 2) && (Math.abs(currentY - getY()) == 1))) {
                if (moveType == 1) { // normal move
                    move(false);
                } else if (moveType == 2) { // capture
                    capture(false);
                } else {
                    setLocation(currentX, currentY);
                }
            } else {
                setLocation(currentX, currentY);
            }
        }
    }
}
