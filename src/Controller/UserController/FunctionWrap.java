package Controller.UserController;


import Controller.UserController.UseCases.UseCase;

/**
 * Created by Karina on 05.03.2016.
 */
public class FunctionWrap{

    private String name;

    private UseCase useCase;

    public FunctionWrap(String name, UseCase useCase) {
        setName(name);
        setUseCase(useCase);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UseCase getUseCase() {
        return useCase;
    }

    public void setUseCase(UseCase useCase) {
        this.useCase = useCase;
    }

}
