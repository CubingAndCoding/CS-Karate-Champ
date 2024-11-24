public class MoveTable {
    public static MoveType[][] moveTable;

    static {
        /*  table:
              n W S
            n 0 0 0
            F 3 5 4
            G 1 6 2 */
        moveTable = new MoveType[3][3];
        moveTable[0][0] = MoveType.NONE;
        moveTable[0][1] = MoveType.NONE;
        moveTable[0][2] = MoveType.NONE;
        moveTable[1][0] = MoveType.PUNCH;
        moveTable[1][1] = MoveType.JUMP;
        moveTable[1][2] = MoveType.ROLL;
        moveTable[2][0] = MoveType.FRONT_KICK;
        moveTable[2][1] = MoveType.BLOCK;
        moveTable[2][2] = MoveType.BACK_KICK;
    }
}
