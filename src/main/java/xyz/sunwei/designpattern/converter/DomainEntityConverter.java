package xyz.sunwei.designpattern.converter;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Domain entity or aggregator converter
 *
 * @param <D> Domain Entity or Domain Aggregator representation's type
 */
public class DomainEntityConverter<D> {
    private final Map<String, Converter<D, Object>> converterMap = new ConcurrentHashMap<>();

    public DomainEntityConverter(final List<Pair<String, Function<D, Object>>> converters) {
        converters.forEach(item -> this.converterMap
                .put(item.getKey(), new Converter<D, Object>(item.getValue())));
    }
    
    public Object convert(D domainEntity, String clsStr) throws DomainEntityConverterNotFoundException {
        Converter<D, Object> convert = this.converterMap.get(clsStr);
        if (null == convert){
            throw new DomainEntityConverterNotFoundException(String.format(
                    "Domain entity converter not found for : %s", clsStr
            ));
        }
        return convert.fromDomainEntity(domainEntity);
    }
}
