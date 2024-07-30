import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.io.*; 
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
        if (enPassant) {
            Actor wActor = getOneObjectAtOffset(0, -1, WPawn.class);
            Actor bActor = getOneObjectAtOffset(0, 1, BPawn.class);
            if (turn == -1 && wActor != null) {
                WPawn wPawn = (WPawn) wActor;
                getWorld().removeObject(wPawn);
            } else if (turn == 1 && bActor != null) {
                BPawn bPawn = (BPawn) bActor;
                getWorld().removeObject(bPawn);
            }
        }
        if (!inCheck()) {
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
        } else if (enPassant) {
            try {
                if (turn == 1) {
                    BPawn bPawn = new BPawn();
                    getWorld().addObject(bPawn, getX(), getY() + 1);
                    bPawn.colour = -1;
                    bPawn.currentX = bPawn.getX();
                    bPawn.currentY = bPawn.getY();
                    bPawn.moved = true;
                    bPawn.enPassantable = true;
                } else {
                    WPawn wPawn = new WPawn();
                    getWorld().addObject(wPawn, getX(), getY() - 1);
                    wPawn.colour = 1;
                    wPawn.currentX = wPawn.getX();
                    wPawn.currentY = wPawn.getY();
                    wPawn.moved = true;
                    wPawn.enPassantable = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setLocation(currentX, currentY);
        } else {
            setLocation(currentX, currentY);
        }
    }

    public void capture(boolean enPassant) {
        Actor actor = getOneIntersectingObject(Piece.class);
        Class<? extends Piece> pieceClass = actor.getClass().asSubclass(Piece.class);
        boolean deletedMoved = false;
        boolean deletedEnPassantable = false;
        if (pieceClass == WPawn.class) {
            WPawn wPawn = (WPawn) actor;
            deletedMoved = wPawn.moved;
            deletedEnPassantable = wPawn.enPassantable;
        }
        if (pieceClass == BPawn.class) {
            BPawn bPawn = (BPawn) actor;
            deletedMoved = bPawn.moved;
            deletedEnPassantable = bPawn.enPassantable;
        }
        removeTouching(Piece.class);
        if (!inCheck()) {
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
        } else {
            try {
                Piece newPiece = pieceClass.newInstance();
                getWorld().addObject(newPiece, getX(), getY());
                newPiece.currentX = newPiece.getX();
                newPiece.currentY = newPiece.getY();
                if (turn == 1) {
                    newPiece.colour = -1;
                } else {
                    newPiece.colour = 1;
                }
                if (newPiece.getClass() == WPawn.class) {
                    WPawn wPawn = (WPawn) newPiece;
                    wPawn.moved = deletedMoved;
                    wPawn.enPassantable = deletedEnPassantable;
                }
                if (newPiece.getClass() == BPawn.class) {
                    BPawn bPawn = (BPawn) newPiece;
                    bPawn.moved = deletedMoved;
                    bPawn.enPassantable = deletedEnPassantable;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setLocation(currentX, currentY);
        }
    }

    public boolean inCheck() {
        WhitePiece.inCheck = false;
        BlackPiece.inCheck = false;
        int wKingX = getWorld().getObjects(WKing.class).get(0).getX();
        int wKingY = getWorld().getObjects(WKing.class).get(0).getY();
        int bKingX = getWorld().getObjects(BKing.class).get(0).getX();
        int bKingY = getWorld().getObjects(BKing.class).get(0).getY();
        if ((wKingX != 0) && (wKingY != 0)) {
            List<BPawn> checkingPawnL = getWorld().getObjectsAt(wKingX - 1, wKingY - 1, BPawn.class);
            BPawn checker = null;
            if (!checkingPawnL.isEmpty()) {
                checker = checkingPawnL.get(0);
            }
            if (checker != null) {
                WhitePiece.inCheck = true;
            }
        }
        if ((wKingX != 7) && (wKingY != 0)) {
            List<BPawn> checkingPawnR = getWorld().getObjectsAt(wKingX + 1, wKingY - 1, BPawn.class);
            BPawn checker = null;
            if (!checkingPawnR.isEmpty()) {
                checker = checkingPawnR.get(0);
            }
            if (checker != null) {
                WhitePiece.inCheck = true;
            }
        }
        if ((bKingX != 0) && (bKingY != 7)) {
            List<WPawn> checkingPawnL = getWorld().getObjectsAt(bKingX - 1, bKingY + 1, WPawn.class);
            WPawn checker = null;
            if (!checkingPawnL.isEmpty()) {
                checker = checkingPawnL.get(0);
            }
            if (checker != null) {
                BlackPiece.inCheck = true;
            }
        }
        if ((bKingX != 7) && (bKingY != 7)) {
            List <WPawn> checkingPawnR = getWorld().getObjectsAt(bKingX + 1, bKingY + 1, WPawn.class);
            WPawn checker = null;
            if (!checkingPawnR.isEmpty()) {
                checker = checkingPawnR.get(0);
            }
            if (checker != null) {
                BlackPiece.inCheck = true;
            }
        }
        checkDiagonal(wKingX, wKingY, -1, -1, 1);
        checkDiagonal(wKingX, wKingY, 1, -1, 1);
        checkDiagonal(wKingX, wKingY, 1, 1, 1);
        checkDiagonal(wKingX, wKingY, -1, 1, 1);
        checkHorizontal(wKingX, wKingY, 1, 1);
        checkHorizontal(wKingX, wKingY, -1, 1);
        checkVertical(wKingX, wKingY, 1, 1);
        checkVertical(wKingX, wKingY, -1, 1);
        checkDiagonal(bKingX, bKingY, -1, -1, -1);
        checkDiagonal(bKingX, bKingY, 1, -1, -1);
        checkDiagonal(bKingX, bKingY, 1, 1, -1);
        checkDiagonal(bKingX, bKingY, -1, 1, -1);
        checkHorizontal(bKingX, bKingY, 1, -1);
        checkHorizontal(bKingX, bKingY, -1, -1);
        checkVertical(bKingX, bKingY, 1, -1);
        checkVertical(bKingX, bKingY, -1, -1);
        List<BKnight> bKnights = getWorld().getObjects(BKnight.class);
        for (BKnight knight : bKnights) {
            if (((Math.abs(wKingX - knight.getX()) == 1) && (Math.abs(wKingY - knight.getY()) == 2)) || ((Math.abs(wKingX - knight.getX()) == 2) && (Math.abs(wKingY - knight.getY()) == 1))) {
                WhitePiece.inCheck = true;
            }
        }
        List<WKnight> wKnights = getWorld().getObjects(WKnight.class);
        for (WKnight knight : wKnights) {
            if (((Math.abs(bKingX - knight.getX()) == 1) && (Math.abs(bKingY - knight.getY()) == 2)) || ((Math.abs(bKingX - knight.getX()) == 2) && (Math.abs(bKingY - knight.getY()) == 1))) {
                BlackPiece.inCheck = true;
            }
        }
        Piece wKing = getWorld().getObjects(WKing.class).get(0);
        List <BKing> aroundKing = wKing.getNeighbours(1, true, BKing.class);
        if (!aroundKing.isEmpty()) {
            return true;
        }
        if ((turn == 1) && (WhitePiece.inCheck == true)) {
            return true;
        } else if ((turn == -1) && (BlackPiece.inCheck == true)) {
            return true;
        } else {
            return false;
        }
    }

    private void checkDiagonal(int startX, int startY, int dx, int dy, int kingColour) {
        for (int i = 1; i < 8; ++i) {
            int x = startX + i * dx;
            int y = startY + i * dy;
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                break;
            }
            List<Piece> piecesAtLocation = getWorld().getObjectsAt(x, y, Piece.class);
            Piece checker = null;
            if (!piecesAtLocation.isEmpty()) {
                checker = piecesAtLocation.get(0);
            }
            if (checker != null) {
                if (kingColour == 1 && (checker instanceof BBishop || checker instanceof BQueen)) {
                    WhitePiece.inCheck = true;
                } else if (kingColour == -1 && (checker instanceof WBishop || checker instanceof WQueen)){
                    BlackPiece.inCheck = true;
                } else {
                    break;
                }
            }
        }
    }

    private void checkHorizontal(int startX, int startY, int dx, int kingColour) {
        for (int i = 1; i < 8; ++i) {
            int x = startX + i * dx;
            if (x < 0 || x > 7) {
                break;
            }
            List<Piece> piecesAtLocation = getWorld().getObjectsAt(x, startY, Piece.class);
            Piece checker = null;
            if (!piecesAtLocation.isEmpty()) {
                checker = piecesAtLocation.get(0);
            }
            if (checker != null) {
                if (kingColour == 1 && (checker instanceof BRook || checker instanceof BQueen)) {
                    WhitePiece.inCheck = true;
                } else if (kingColour == -1 && (checker instanceof WRook || checker instanceof WQueen)) {
                    BlackPiece.inCheck = true;
                } else {
                    break;
                }
            }
        }
    }

    private void checkVertical(int startX, int startY, int dy, int kingColour) {
        for (int i = 1; i < 8; ++i) {
            int y = startY + i * dy;
            if (y < 0 || y > 7) {
                break;
            }
            List<Piece> piecesAtLocation = getWorld().getObjectsAt(startX, y, Piece.class);
            Piece checker = null;
            if (!piecesAtLocation.isEmpty()) {
                checker = piecesAtLocation.get(0);
            }
            if (checker != null) {
                if (kingColour == 1 && (checker instanceof BRook || checker instanceof BQueen)) {
                    WhitePiece.inCheck = true;
                } else if (kingColour == -1 && (checker instanceof WRook || checker instanceof WQueen)){
                    BlackPiece.inCheck = true;
                } else {
                    break;
                }
            }
        }
    }
}
