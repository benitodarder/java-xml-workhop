package local.tin.tests.xml.tools.console;

/**
 *
 * @author benitodarder
 */
public class Common {
    
    private Common() {
    }
    
    public static Common getInstance() {
        return CommonHolder.INSTANCE;
    }
    
    private static class CommonHolder {

        private static final Common INSTANCE = new Common();
    }
    
    public String getArgument(String argument, String[] args) {
        int i = 0;
        for (; i < args.length; i++) {
            if (argument.equals(args[i])) {
                break;
            }
        }
        if (i >= args.length - 1) {
            return null;
        }
        return args[i + 1];
    }    
}
