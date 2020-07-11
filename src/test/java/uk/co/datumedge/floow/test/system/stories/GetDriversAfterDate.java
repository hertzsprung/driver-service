package uk.co.datumedge.floow.test.system.stories;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import uk.co.datumedge.floow.test.system.DriverServiceSteps;
import uk.co.datumedge.floow.test.system.RestSteps;

import static org.jbehave.core.reporters.Format.CONSOLE;

public class GetDriversAfterDate extends JUnitStory {
    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .usePendingStepStrategy(new FailingUponPendingStep())
                .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(CONSOLE));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new DriverServiceSteps(), new RestSteps());
    }
}
