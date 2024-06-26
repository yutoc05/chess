import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class BRook extends BlackPiece
{
    public void act()
    {
        if (!dragging()) {
            int moveType = moveType();
            if ((currentX == getX() && currentY != getY()) || (currentX != getX() && currentY == getY())) {
                if (moveType == 1) { // normal move
                    if (currentX == 0 && currentY == 0) {
                        canCastleLong = false;
                    } else if (currentX == 7 && currentY == 0) {
                        canCastleShort = false;
                    }
                    move(false);
                } else if (moveType == 2) { // capture
                    if (currentX == 0 && currentY == 0) {
                        canCastleLong = false;
                    } else if (currentX == 7 && currentY == 0) {
                        canCastleShort = false;
                    }
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
