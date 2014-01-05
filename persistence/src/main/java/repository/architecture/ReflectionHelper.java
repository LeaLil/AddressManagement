package repository.architecture;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for reflection matters
 *
 * @author stibe
 * @since 1.0.0
 */
public class ReflectionHelper {

    // do not instantiate this class...
    private ReflectionHelper() {
    }

    /**
     * Determines the Type Parameter for the particular class instance.
     * This method assumes that the class is not a raw-type and defines
     * the actual type parameters.
     *
     * @return The first type parameter for the particular class
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> determineFirstParameterClass(Class<?> classUnderExamination) throws ReflectionHelperException {
        return determineParameterClass(classUnderExamination, 0);
    }

    /**
     * Determines the Type Parameter for the particular class instance.
     * This method assumes that the class is not a raw-type and defines
     * the actual type parameters.
     *
     * @return The type parameter with the given index for the particular class
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> determineParameterClass(Class<?> classUnderExamination, int index) throws ReflectionHelperException {
        if (index < 0) {
            throw new ReflectionHelperException("Param 'index' must not be lower than 0!");
        }
        if (classUnderExamination == null) {
            throw new ReflectionHelperException("Param 'classUnderExamination' must not be null!");
        }
        if (classUnderExamination == Class.class) {
            throw new ReflectionHelperException("Can't resolve a parameter class on type Class.class");
        }
        if (!(classUnderExamination.getGenericSuperclass() instanceof ParameterizedType)) {
            throw new ReflectionHelperException("Class: " + classUnderExamination.getName() + " must be parameterized and extend a parameterized superclass...)");
        }

        ParameterizedType type = (ParameterizedType) classUnderExamination.getGenericSuperclass();
        if (index > type.getActualTypeArguments().length - 1) {
            throw new ReflectionHelperException("No Type Parameter define for: '" + type + "' with index '" + index + "'");
        }
        return (Class<T>) type.getActualTypeArguments()[index];
    }

    public static Field resolveField(String path, Class<?> classUnderExamination) throws ReflectionHelperException {
        if (StringUtils.isEmpty(path)) {
            throw new ReflectionHelperException("Param 'path' must not be empty");
        }
        if (classUnderExamination == null) {
            throw new ReflectionHelperException("Param 'classUnderExamination' must not be null");
        }
        if (classUnderExamination == Class.class) {
            throw new ReflectionHelperException("Can't resolve a value on type Class.class");
        }
        String[] tokens = path.split("\\.");

        if (ArrayUtils.isEmpty(tokens)) {
            List<String> list = new ArrayList<String>();
            list.add(path);
            return resolveField(list, classUnderExamination);
        } else {
            return resolveField(Arrays.asList(tokens), classUnderExamination);
        }
    }

    private static Field resolveField(List<String> path, Class<?> classUnderExamination) throws ReflectionHelperException {
        if (CollectionUtils.isEmpty(path)) {
            throw new ReflectionHelperException("Path must not be empty!");
        }
        if (classUnderExamination == null) {
            throw new ReflectionHelperException("Param 'classUnderExamination' must not be null");
        }
        if (classUnderExamination == Class.class) {
            throw new ReflectionHelperException("Can't resolve a value on type Class.class");
        }
        String currentPropertyName = path.get(0);
        try {
            Field field = classUnderExamination.getDeclaredField(currentPropertyName);

            if (path.size() == 1) {
                return field;
            } else {
                return resolveField(path.subList(1, path.size()), field.getType());
            }
        } catch (NoSuchFieldException e) {
            Class<?> superClass = classUnderExamination.getSuperclass();

            if (superClass == null) {
                throw new ReflectionHelperException("Could not find a property with the given path!");
            }

            return resolveField(path, superClass);
        }
    }

    public static Object resolveValue(String path, Object object) throws ReflectionHelperException, IllegalAccessException {
        if (StringUtils.isEmpty(path)) {
            throw new ReflectionHelperException("Param 'path' must not be empty");
        }
        if (object == null) {
            throw new ReflectionHelperException("Param 'object' must not be null");
        }

        if (object.getClass() == Class.class) {
            throw new ReflectionHelperException("Can't resolve a value on type Class.class");
        }

        String[] tokens = path.split("\\.");
        if (resolveField(path, object.getClass()) == null) {
            throw new ReflectionHelperException("Illegal path: " + path.toString());
        }


        if (ArrayUtils.isEmpty(tokens)) {
            List<String> list = new ArrayList<String>();
            list.add(path);
            return resolveValue(list, object);
        } else {
            return resolveValue(Arrays.asList(tokens), object);
        }
    }

    private static Object resolveValue(List<String> path, Object object) throws ReflectionHelperException, IllegalAccessException {
        if (CollectionUtils.isEmpty(path)) {
            throw new ReflectionHelperException("Path must not be empty!");
        }

        if (object == null) {
            throw new ReflectionHelperException("Object must not be null!");
        }

        if (object.getClass() == Class.class) {
            throw new ReflectionHelperException("Can't resolve a value on type Class.class");
        }

        if (resolveField(path, object.getClass()) == null) {
            throw new ReflectionHelperException("Illegal path: " + path.toString());
        }

        String currentPropertyName = path.get(0);
        try {
            Field field = object.getClass().getDeclaredField(currentPropertyName);

            field.setAccessible(true);
            if (path.size() == 1) {
                return field.get(object);
            } else {
                return resolveValue(path.subList(1, path.size()), field.get(object));
            }
        } catch (NoSuchFieldException e) {
            Class<?> superClass = object.getClass().getSuperclass();

            if (superClass == null) {
                throw new ReflectionHelperException("Could not find a property with the given path!");
            }
            return resolveValue(path, superClass);
        }
    }
}
