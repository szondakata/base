package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainController mockTC;
    TrainUser mockTU;
    TrainSensor TS;

    @Before
    public void before() {
        mockTC = mock(TrainController.class);
        mockTU = mock(TrainUser.class);
        TS = new TrainSensorImpl(mockTC, mockTU);
    }

    @Test
    public void TestSpeedLimit() {
        Assert.assertEquals(TS.getSpeedLimit(), 130);
    }

    @Test
    public void TestNegativeSpeedLimit(){
        TS.overrideSpeedLimit(-10);
        verify(mockTU).setAlarmState(true);
    }

    @Test
    public void TestTooBigSpeedLimit(){
        TS.overrideSpeedLimit(543);
        verify(mockTU).setAlarmState(true);
    }

    @Test
    public void TestReferenceSpeed() {
        when(mockTC.getReferenceSpeed()).thenReturn(130);
    }

    @Test
    public void TestSmallerThanHalfSpeedLimit(){
        TS.overrideSpeedLimit(64);
        verify(mockTU).setAlarmState(true);
    }

    @Test
    public void TestInRangeLimit(){
        TS.overrideSpeedLimit(65);
        verify(mockTU).setAlarmState(false);
    }
}
