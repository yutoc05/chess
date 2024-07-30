import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.JOptionPane;
public class WPawn extends WhitePiece
{
    boolean moved = false;
    boolean enPassantable = false;
    public void act()
    {
        if (!dragging()) {
            int moveType = moveType();
            if (moveType == 1) { // normal move
                if (currentX == getX() && currentY == getY() + 1) {
                    move(false);
                } else if ((currentX == getX()) && (currentY == getY() + 2) && !moved) {
                    move(false);
                    moved = true;
                    enPassantable = true;
                } else if (((currentX == getX() + 1) || (currentX == getX() - 1)) && (currentY == getY() + 1)) {
                    Actor actor = getOneObjectAtOffset(0, 1, BPawn.class);
                    if (actor != null && actor instanceof BPawn) {
                        BPawn bPawn = (BPawn) actor;
                        if (bPawn.enPassantable) {
                            move(true);
                        } else {
                            setLocation(currentX, currentY);
                        }
                    } else {
                        setLocation(currentX, currentY);
                    }
                } else {
                    setLocation(currentX, currentY);
                }
            } else if (moveType == 2) { // capture
                if (((currentX == getX() + 1) || (currentX == getX() - 1)) && (currentY == getY() + 1)) {
                    capture(false);
                } else {
                    setLocation(currentX, currentY);
                }
            } else {
                setLocation(currentX, currentY);
            }
            if ((moveType != 0) && (currentY == 0)) { // promotion
                String[] options = {"Queen", "Rook", "Bishop", "Knight"};
                String choice = (String) JOptionPane.showInputDialog(
                        null,
                        "Choose piece for promotion:",
                        "Pawn Promotion",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                    );
                if (choice.equals("Queen")) {
                    WQueen wQueen = new WQueen();
                    getWorld().addObject(wQueen,currentX,currentY);
                    wQueen.currentX = currentX;
                    wQueen.currentY = currentY;
                    wQueen.colour = 1;
                } else if (choice.equals("Rook")) {
                    WRook wRook = new WRook();
                    getWorld().addObject(wRook,currentX,currentY);
                    wRook.currentX = currentX;
                    wRook.currentY = currentY;
                    wRook.colour = 1;
                } else if (choice.equals("Bishop")) {
                    WBishop wBishop = new WBishop();
                    getWorld().addObject(wBishop,currentX,currentY);
                    wBishop.currentX = currentX;
                    wBishop.currentY = currentY;
                    wBishop.colour = 1;
                } else if (choice.equals("Knight")) {
                    WKnight wKnight = new WKnight();
                    getWorld().addObject(wKnight,currentX,currentY);
                    wKnight.currentX = currentX;
                    wKnight.currentY = currentY;
                    wKnight.colour = 1;
                }
                getWorld().removeObject(this);
            }
        }
    }
}
