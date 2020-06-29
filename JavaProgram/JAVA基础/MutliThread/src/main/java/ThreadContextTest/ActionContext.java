package ThreadContextTest;

public class ActionContext {
    private final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    private static class ContextBuilder {
        private static final ActionContext actionContext = new ActionContext();
    }

    public static ActionContext getInstance() {
        return ContextBuilder.actionContext;
    }

    public Context getContext() {
        return threadLocal.get();
    }
}
