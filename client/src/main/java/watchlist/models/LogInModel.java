package watchlist.models;

public class LogInModel {
    private Account account;
    private String errorMsg;
    private boolean success;

    public LogInModel() {
    }
    public LogInModel(Account account, String errorMsg) {
        this.account = account;
        this.errorMsg = errorMsg;
    }
    public LogInModel(Account account, String errorMsg, boolean success) {
        this(account, errorMsg);
        this.success = success;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
