import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
public class Piece extends Actor
{
    static int turn = 1; // white is 1, black is -1
    int colour = 0; // white is 1, black is -1
    int currentX = 0;
    int currentY = 0;
    public void act()
    {
        // Add your action code here.
    }

    public int moveType() { // 0=invalid,1=std move,2=capture
        if (!(this instanceof WKnight) && !(this instanceof BKnight)) {
            int moveDistance = Math.max(Math.abs(getX() - currentX), Math.abs(getY() - currentY));
            for (int i = 1; i < moveDistance; ++i) {
                //System.out.println("getX()=" + getX() + ", currentX=" + currentX + ", getY()=" + getY() + ", currentY=" + currentY);
                if ((getX() < currentX) && (getY() < currentY)) {
                    if (getOneObjectAtOffset(i, i, Piece.class) != null) {
                        return 0;
                    }
                } else if ((getX() < currentX) && (getY() > currentY)) {
                    if (getOneObjectAtOffset(i, -i, Piece.class) != null) {
                        return 0;
                    }
                } else if ((getX() > currentX) && (getY() > currentY)) {
                    if (getOneObjectAtOffset(-i, -i, Piece.class) != null) {
                        return 0;
                    }
                } else if ((getX() > currentX) && (getY() < currentY)) {
                    if (getOneObjectAtOffset(-i, i, Piece.class) != null) {
                        return 0;
                    }
                } else if (getX() < currentX) {
                    if (getOneObjectAtOffset(i, 0, Piece.class) != null) {
                        return 0;
                    }
                } else if (getX() > currentX) {
                    if (getOneObjectAtOffset(-i, 0, Piece.class) != null) {
                        return 0;
                    }
                } else if (getY() < currentY) {
                    if (getOneObjectAtOffset(0, i, Piece.class) != null) {
                        return 0;
                    }
                } else if (getY() > currentY) {
                    if (getOneObjectAtOffset(0, -i, Piece.class) != null) {
                        return 0;
                    }
                }
            }
        }
        Actor actor = getOneIntersectingObject(Piece.class);
        if (actor != null && actor instanceof Piece) {
            Piece piece = (Piece) actor;
            if (piece.colour == this.colour) {
                return 0;
            }
            return 2;
        }
        return 1;
    }

    public void move(boolean enPassant) {
        if (!inCheck()) {
            setLocation(getX(), getY());
            currentX = getX();
            currentY = getY();
            turn *= -1;
            if (!enPassant) {
                List<WPawn> wPawns = getWorld().getObjects(WPawn.class);
                for (WPawn pawn : wPawns) {
                    pawn.enPassantable = false;
                }
                List<BPawn> bPawns = getWorld().getObjects(BPawn.class);
                for (BPawn pawn : bPawns) {
                    pawn.enPassantable = false;
                }
            }
        }
    }

    public void capture(boolean enPassant) {
        removeTouching(Piece.class);
        setLocation(getX(), getY());
        currentX = getX();
        currentY = getY();
        turn *= -1;
        if (!enPassant) {
            List<WPawn> wPawns = getWorld().getObjects(WPawn.class);
            for (WPawn pawn : wPawns) {
                pawn.enPassantable = false;
            }
            List<BPawn> bPawns = getWorld().getObjects(BPawn.class);
            for (BPawn pawn : bPawns) {
                pawn.enPassantable = false;
            }
        }
    }
    
    public boolean inCheck() {
        //if (
        return false;
    }
}
