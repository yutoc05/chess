import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class WRook extends WhitePiece
{
    public void act()
    {
        if (!dragging()) {
            int moveType = moveType();
            if ((currentX == getX() && currentY != getY()) || (currentX != getX() && currentY == getY())) {
                if (moveType == 1) { // normal move
                    if (currentX == 0 && currentY == 7) {
                        canCastleLong = false;
                    } else if (currentX == 7 && currentY == 7) {
                        canCastleShort = false;
                    }
                    move(false);
                } else if (moveType == 2) { // capture
                    if (currentX == 0 && currentY == 7) {
                        canCastleLong = false;
                    } else if (currentX == 7 && currentY == 7) {
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