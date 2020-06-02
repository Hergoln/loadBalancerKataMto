package edu.iis.mto.serverloadbalancer;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public class ServerAndVmsConverter implements ArgumentConverter {

    public ServerAndVmsConverter() { }

    @Override
    public Object convert(Object source, ParameterContext context)
            throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new IllegalArgumentException(
                    "The argument should be a string: " + source);
        }
        try {
            String[] parts = ((String) source).split(":");

            int serverCapacity = Integer.parseInt(parts[0]);
            int vmsSize = Integer.parseInt(parts[1]);
            double loadPercentage = Double.valueOf(parts[2]);

            TestArgs args = new TestArgs();
            args.serverCapacity = serverCapacity;
            args.loadPercentage = loadPercentage;
            args.vmsSize = vmsSize;

            return args;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert", e);
        }
    }
}
