package yaolong.base.mvc.validation;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

/**
 *
 * @author
 *
 */
public class Validator implements ValidationProvider<ValidatorConfiguration> {

	@Override
	public ValidatorConfiguration createSpecializedConfiguration(BootstrapState state) {
		return ValidatorConfiguration.class.cast(new ConfigurationImpl(this));
	}

	@Override
	public Configuration<?> createGenericConfiguration(BootstrapState state) {
		return null;
	}

	@Override
	public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
		return null;
	}

}
