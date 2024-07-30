import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class WKing extends WhitePiece
{
    public void act()
    {
        if (!dragging()) {
            int moveType = moveType();
            if (((currentX == getX()) && (Math.abs(currentY - getY())) == 1) || ((Math.abs(currentX - getX()) == 1) && (currentY == getY())) || ((Math.abs(currentX - getX()) == 1) && (Math.abs(currentY - getY()) == 1))) {
                if (moveType == 1) { // normal move
                    move(false);
                    canCastleShort = false;
                    canCastleLong = false;
                } else if (moveType == 2) { // capture
                    capture(false);
                    canCastleShort = false;
                    canCastleLong = false;
                } else {
                    setLocation(currentX, currentY);
                }
            } else if (canCastleShort && (currentY == getY()) && (currentX < getX()) && !inCheck) {
                Actor actor = getOneObjectAtOffset(1, 0, WRook.class);
                if (moveType != 0 && actor != null && actor instanceof WRook) {
                    canCastleShort = false;
                    canCastleLong = false;
                    setLocation(currentX + 2, currentY);
                    currentX = getX();
                    WRook wRook = getWorld().getObjectsAt(7, currentY, WRook.class).get(0);
                    wRook.setLocation(currentX - 1, currentY);
                    wRook.currentX = actor.getX();
                    turn *= -1;
                } else {
                    setLocation(currentX, currentY);
                }
            } else if (canCastleLong && (currentY == getY()) && (currentX > getX()) && !inCheck) {
                Actor actor = getOneObjectAtOffset(-2, 0, WRook.class);
                if (moveType != 0 && actor != null && actor instanceof WRook) {
                    canCastleShort = false;
                    canCastleLong = false;
                    setLocation(currentX - 2, currentY);
                    currentX = getX();
                    WRook wRook = getWorld().getObjectsAt(0, currentY, WRook.class).get(0);
                    wRook.setLocation(currentX + 1, currentY);
                    wRook.currentX = wRook.getX();
                    turn *= -1;
                } else {
                    setLocation(currentX, currentY);
                }
            } else {
                setLocation(currentX, currentY);
            }
        }
    }
}
