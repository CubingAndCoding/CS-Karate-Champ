public class MoveTable {
    public static MoveType[][] moveTable;

    static {
        /*  table:
              n W S
            n 0 0 0
            F 0 3 6
            G 0 1 2
            H 0 5 4 */
        moveTable = new MoveType[4][3];
        moveTable[0][0] = MoveType.NONE;
        moveTable[0][1] = MoveType.NONE;
        moveTable[0][2] = MoveType.NONE;
        moveTable[1][0] = MoveType.NONE;
        moveTable[2][0] = MoveType.NONE;
        moveTable[3][0] = MoveType.NONE;
        moveTable[1][1] = MoveType.PUNCH;
        moveTable[1][2] = MoveType.BLOCK;
        moveTable[2][1] = MoveType.FRONT_KICK;
        moveTable[2][2] = MoveType.BACK_KICK;
        moveTable[3][1] = MoveType.JUMP;
        moveTable[3][2] = MoveType.DUCK;
    }
}
