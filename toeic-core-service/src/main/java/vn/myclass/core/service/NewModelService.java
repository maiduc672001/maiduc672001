package vn.myclass.core.service;

import vn.myclass.core.dto.NewModel;

import java.util.List;

public interface NewModelService{
    void saveNewModel(NewModel newModel);
    NewModel upDateModel(NewModel newModel);
    Integer deleteModel(List<Integer> ids);
}
