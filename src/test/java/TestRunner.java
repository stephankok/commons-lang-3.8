import org.junit.runner.Result;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.builder.*;
import org.apache.commons.lang3.concurrent.*;
import org.apache.commons.lang3.event.*;
import org.apache.commons.lang3.exception.*;
import org.apache.commons.lang3.math.*;
import org.apache.commons.lang3.mutable.*;
import org.apache.commons.lang3.reflect.*;
import org.apache.commons.lang3.test.*;
import org.apache.commons.lang3.text.*;
import org.apache.commons.lang3.text.translate.*;
import org.apache.commons.lang3.time.*;
import org.apache.commons.lang3.tuple.*;

public class TestRunner {
	private final static int AMOUNTOFTESTS = 4114;
	
	public static void main(String[] args) 
	{
//		Check arguments
		int repeats = validateArgument(args);
		
		System.out.println("Repeating " + repeats + " times.");
		runTests(repeats);
		
		System.exit(0);
	}

	public static int validateArgument(String[] args) {
		if (args.length != 1) {
			System.out.println("You must specify the amount of loops by parsing one argument");
			System.exit(1);
		}
		
		try {
			return Integer.parseInt(args[0]);			
		}
		catch (NumberFormatException e) {
			System.out.println("Parsed argument is not an integer: " + e.getMessage());
			System.exit(2);
		}
		
		return 0;
	}

	public static void runTests(int repeats) {
		for (int i = 0; i < repeats; i++) {
			Result result = runtests();
			if (!result.wasSuccessful() || result.getRunCount() != AMOUNTOFTESTS) {
				printResult(result, i);
				System.exit(3);
			}
		}
	}
	
	private static void printResult(Result result, int run) {
		System.out.println("Failed to run tests all " + AMOUNTOFTESTS + " tests succesfull");
		System.out.println("Run: " + run);
		System.out.println("Fails: " + result.getFailureCount());
		System.out.println("Runtime: " + result.getRunTime());
		System.out.println("RunCount: " + result.getRunCount());
		System.out.println("IgnoreCount: " + result.getIgnoreCount() + "\n");
		System.out.print("Failed runs: ");
		for (int i = 0; i < result.getFailures().size(); i++) {			
			System.out.println("    " + result.getFailures().get(i));			
			System.out.println("    " + result.getFailures().get(i).getMessage());
			System.out.println("    " + result.getFailures().get(i).getTrace());
			System.out.println("    " + result.getFailures().get(i).getTestHeader());
			System.out.println("    ");
		}
	}
	
	private static Result runtests() {	
		return org.junit.runner.JUnitCore.runClasses(
//				org.apache.commons.lang3
				AnnotationUtilsTest.class,
				ArchUtilsTest.class,
				ArrayUtilsAddTest.class,
				ArrayUtilsInsertTest.class,
				ArrayUtilsRemoveMultipleTest.class,
				ArrayUtilsRemoveTest.class,
				ArrayUtilsTest.class,
				BitFieldTest.class,
				BooleanUtilsTest.class,
				CharEncodingTest.class,
				CharRangeTest.class,
				CharSequenceUtilsTest.class,
				CharSetTest.class,
				CharSetUtilsTest.class,
//				CharUtilsPerfRun.class,
				CharUtilsTest.class,
				ClassPathUtilsTest.class,
				ClassUtilsTest.class,
				ConversionTest.class,
				EnumUtilsTest.class,
//				HashSetvBitSetTest.class, No runnable methods
				JavaVersionTest.class,
				LocaleUtilsTest.class,
				NotImplementedExceptionTest.class,
				ObjectUtilsTest.class,
				RandomStringUtilsTest.class,
				RandomUtilsTest.class,
				RangeTest.class,
				RegExUtilsTest.class,
				SerializationUtilsTest.class,
				StringEscapeUtilsTest.class,
				StringUtilsContainsTest.class,
				StringUtilsEmptyBlankTest.class,
				StringUtilsEqualsIndexOfTest.class,
				StringUtilsIsTest.class,
				StringUtilsStartsEndsWithTest.class,
				StringUtilsSubstringTest.class,
				StringUtilsTest.class,
				StringUtilsTrimStripTest.class,
				SystemUtilsTest.class,
				ThreadUtilsTest.class,
				ValidateTest.class,
//				org.apache.commons.lang3.builder
				CompareToBuilderTest.class,
				DefaultToStringStyleTest.class,
				DiffBuilderTest.class,
				DiffResultTest.class,
				DiffTest.class,
				EqualsBuilderTest.class,
				HashCodeBuilderAndEqualsBuilderTest.class,
				HashCodeBuilderTest.class,
				JsonToStringStyleTest.class,
				MultilineRecursiveToStringStyleTest.class,
				MultiLineToStringStyleTest.class,
				NoClassNameToStringStyleTest.class,
				NoFieldNamesToStringStyleTest.class,
				RecursiveToStringStyleTest.class,
				ReflectionDiffBuilderTest.class,
				ReflectionToStringBuilderConcurrencyTest.class,
				ReflectionToStringBuilderExcludeNullValuesTest.class,
				ReflectionToStringBuilderExcludeTest.class,
				ReflectionToStringBuilderExcludeWithAnnotationTest.class,
				ReflectionToStringBuilderMutateInspectConcurrencyTest.class,
				ReflectionToStringBuilderSummaryTest.class,
				ReflectionToStringBuilderTest.class,
				ShortPrefixToStringStyleTest.class,
				SimpleToStringStyleTest.class,
				StandardToStringStyleTest.class,
				ToStringBuilderTest.class,
				ToStringStyleConcurrencyTest.class,
				ToStringStyleTest.class,
//				org.apache.commons.lang3.concurrent
//				AbstractConcurrentInitializerTest.class, null
				AtomicInitializerTest.class,
				AtomicSafeInitializerTest.class,
				BackgroundInitializerTest.class,
				BasicThreadFactoryTest.class,
				CallableBackgroundInitializerTest.class,
				CircuitBreakingExceptionTest.class,
				ConcurrentUtilsTest.class,
				ConstantInitializerTest.class,
				EventCountCircuitBreakerTest.class,
				LazyInitializerTest.class,
				MemoizerTest.class,
				MultiBackgroundInitializerTest.class,
				ThresholdCircuitBreakerTest.class,
				TimedSemaphoreTest.class,
//				org.apache.commons.lang3.event
				EventListenerSupportTest.class,
				EventUtilsTest.class,
//				org.apache.commons.lang3.exception
//				AbstractExceptionContextTest.class, null
//				AbstractExceptionTest.class, No runnable methods
				CloneFailedExceptionTest.class,
				ContextedExceptionTest.class,
				ContextedRuntimeExceptionTest.class,
				DefaultExceptionContextTest.class,
				ExceptionUtilsTest.class,
//				org.apache.commons.lang3.math
				FractionTest.class,
				IEEE754rUtilsTest.class,
				NumberUtilsTest.class,
//				org.apache.commons.lang3.mutable
				MutableBooleanTest.class,
				MutableByteTest.class,
				MutableDoubleTest.class,
				MutableFloatTest.class,
				MutableIntTest.class,
				MutableLongTest.class,
				MutableObjectTest.class,
				MutableShortTest.class,
//				org.apache.commons.lang3.reflect
				ConstructorUtilsTest.class,
				FieldUtilsTest.class,
				InheritanceUtilsTest.class,
				MethodUtilsTest.class,
				TypeLiteralTest.class,
				TypeUtilsTest.class,
//				org.apache.commons.lang3.reflect.testbed
//				Ambig.class,
//				Annotated.class,
//				AnotherChild.class,
//				AnotherParent.class,
//				Bar.class,
//				Foo.class,
//				GenericConsumer.class,
//				GenericParent.class,
//				GenericTypeHolder.class,
//				Grandchild.class,
//				Parent.class,
//				PrivatelyShadowedChild.class,
//				PublicChild.class,
//				PubliclyShadowedChild.class,
//				StaticContainer.class,
//				StaticContainerChild.class,
//				StringParameterizedChild.class,
//				org.apache.commons.lang3.test
//				NotVisibleExceptionFactory.class,
//				SystemDefaults.class,
//				SystemDefaultsSwitch.class,
				SystemDefaultsSwitchTest.class,
//				org.apache.commons.lang3.text
				CompositeFormatTest.class,
				ExtendedMessageFormatTest.class,
				FormattableUtilsTest.class,
				StrBuilderAppendInsertTest.class,
				StrBuilderTest.class,
				StrLookupTest.class,
				StrMatcherTest.class,
				StrSubstitutorTest.class,
				StrTokenizerTest.class,
				WordUtilsTest.class,
//				org.apache.commons.lang3.text.translate
				EntityArraysTest.class,
				LookupTranslatorTest.class,
				NumericEntityEscaperTest.class,
				NumericEntityUnescaperTest.class,
				OctalUnescaperTest.class,
				UnicodeEscaperTest.class,
				UnicodeUnescaperTest.class,
				UnicodeUnpairedSurrogateRemoverTest.class,
//				org.apache.commons.lang3.time
				DateFormatUtilsTest.class,
				DateUtilsFragmentTest.class,
				DateUtilsRoundingTest.class,
				DateUtilsTest.class,
				DurationFormatUtilsTest.class,
				FastDateFormat_ParserTest.class,
				FastDateFormat_PrinterTest.class,
				FastDateFormatTest.class,
				FastDateParser_MoreOrLessTest.class,
				FastDateParser_TimeZoneStrategyTest.class,
				FastDateParserSDFTest.class,
				FastDateParserTest.class,
				FastDatePrinterTest.class,
				FastDatePrinterTimeZonesTest.class,
				FastTimeZoneTest.class,
				GmtTimeZoneTest.class,
				StopWatchTest.class,
				WeekYearTest.class,
//				org.apache.commons.lang3.tuple
				ImmutablePairTest.class,
				ImmutableTripleTest.class,
				MutablePairTest.class,
				MutableTripleTest.class,
				PairTest.class,
				TripleTest.class
				);
	}
}