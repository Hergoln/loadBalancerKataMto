package edu.iis.mto.serverloadbalancer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest {

    @ParameterizedTest(name = "{index} [{arguments}]")
    @CsvSource({"1:1:100.0", "2:1:50.0", "4:1:25.0", "10:5:50.0", "20:15:75.0"})
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm(@ConvertWith(ServerAndVmsConverter.class) TestArgs args) {
        Server theServer = a(server().withCapacity(args.serverCapacity));
        Vm theVm = a(vm().ofSize(args.vmsSize));
        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasLoadPercentageOf(args.loadPercentage));
        assertThat("the server should contain vm", theServer.contains(theVm));
    }


}
