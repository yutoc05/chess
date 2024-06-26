import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class BBishop extends BlackPiece
{
    public void act()
    {
        if (!dragging()) {
            int moveType = moveType();
            if (currentX != getX() && currentY != getY() && (Math.abs(currentX - getX()) == Math.abs(currentY - getY()))) {
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
