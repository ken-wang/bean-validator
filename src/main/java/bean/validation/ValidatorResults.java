package bean.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ValidatorResults {

    private Map<String, List<ValidatorResult<?>>> map = new HashMap<>();
    private boolean isAllValid = true;

    public void addAll(List<ValidatorResult<?>> results) {
        for (ValidatorResult<?> result : results) {
            List<ValidatorResult<?>> fieldResults = map.get(result.getName());
            if (fieldResults == null) {
                fieldResults = new ArrayList<>();
                map.put(result.getName(), fieldResults);
            }
            fieldResults.add(result);
            this.isAllValid &= result.isValid();
        }
    }

    public boolean isAllValid() {
        return isAllValid;
    }

    public void setAllValid(boolean isAllValid) {
        this.isAllValid = isAllValid;
    }

    public Map<String, List<ValidatorResult<?>>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<ValidatorResult<?>>> map) {
        this.map = map;
    }
    
    public String getResultsMessage() {
        if (isAllValid) {
          return "All fields are valid";
        } else {
          StringBuilder builder = new StringBuilder("");
          for (Entry<String, List<ValidatorResult<?>>> entry: map.entrySet()) {
              for(ValidatorResult<?> result : entry.getValue()) {
//                  if(result.isValid()) {
//                      continue;
//                  }
                  builder.append(result.toString()).append("\n");
              }
          }
          return builder.toString();
        }

      }

}
