
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import util.execution.DummyStringToStringExecution;
import util.execution.production.ExecutionHolder;
import util.execution.production.ExecutionQualifier;



@RequestScoped
@Named(value = "utilRequestBean")
public class UtilCallerRequestBean {
    private static final Logger LOGGER = Logger.getLogger(SendJmsMessageRequestBean.class.getCanonicalName());
    private static final String CLIENT_ID_GLOBAL = null;

    // ///////////////////////////////////////////////
    // BEGIN: STATE
    // ///////////////////////////////////////////////
    /**
     * This injection is an obscure malabarism of indirection. We have a producer that knows how to produce this type of
     * bean. The producer is interested in the qualifier that is put on the injection point it will use the value on the
     * qualifier when initializing the resources that go onto the holder. It is important that the whole production
     * chain is given this qualifier value.
     *
     * In this absurd exmaple the value is irrelevant we are just checking the CDI production implementaiton,
     */
    @Inject
    @ExecutionQualifier(value = "teslaCarsAreBeautifulAndEcoFirendly")
    ExecutionHolder executionHolder;

    @Inject
    private transient FacesContext facesContext;

    private String userInput = "computeLength";
    private int inputLength = userInput.length();

    // ///////////////////////////////////////////////
    // BEGIN: ACTION
    // ///////////////////////////////////////////////

    /** form submission */
    public void execute() {
        try {

            // 
            // compute the number of characters writen by the user 
            // frivolous null -> str mapping
            inputLength = getDummyExecution().execute(userInput).length();

        } catch (Exception e) {
            facesContext.addMessage(CLIENT_ID_GLOBAL, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.
                    getStackTrace().toString()));
        }
    }

    // ///////////////////////////////////////////////
    // BEGIN: BOILER PLATE CODE
    // ///////////////////////////////////////////////
    /**
     * the only point here is that the DummyStringToStringExecution implementation class should have been properly
     * initialized with the qualifier value.
     */
    private DummyStringToStringExecution getDummyExecution() {
        return executionHolder.getExecutionFor(DummyStringToStringExecution.class);
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public int getInputLength() {
        return inputLength;
    }

    public void setInputLength(int inputLength) {
        this.inputLength = inputLength;
    }

}
