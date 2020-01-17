package ua.ivan909020.admin.utils;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public class FormatDateTimeMethodModel implements TemplateMethodModelEx {

    public Object exec(List args) throws TemplateModelException {
        if (args.size() != 2) {
            throw new TemplateModelException("Wrong arguments");
        }
        TemporalAccessor time = (TemporalAccessor) ((StringModel) args.get(0)).getWrappedObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(((SimpleScalar) args.get(1)).getAsString());
        return formatter.format(time);
    }

}
