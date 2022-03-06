package aftermealstudio.findteammate.model.exception;

public class MemberAlreadyExistException extends RuntimeException {
    public MemberAlreadyExistException() {
        super("Member Already Exist");
    }
}
