package org.uberfire.client.mvp;

import java.util.HashSet;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.uberfire.client.workbench.widgets.events.SelectPlaceEvent;
import org.uberfire.shared.mvp.PlaceRequest;
import org.uberfire.shared.mvp.impl.DefaultPlaceRequest;

/**
 * Initial (poor coverage) integration tests for PlaceManager, PanelManager and
 * life-cycle events. There remains a lot more work to do in this class.
 */
public class AbstractWorkbenchScreenActivityTest extends BaseWorkbenchTest {

    @Test
    //Reveal a Place once. It should be launched, OnStart and OnReveal called once.
    public void testGoToOnePlace() throws Exception {
        final PlaceRequest somewhere = new DefaultPlaceRequest( "Somewhere" );

        final WorkbenchScreenActivity activity = new MockWorkbenchScreenActivity( placeManager );
        final WorkbenchScreenActivity spy = spy( activity );

        when( activityManager.getActivities( somewhere ) ).thenReturn( new HashSet<Activity>( 1 ) {{
            add( spy );
        }} );

        placeManager.goTo( somewhere );

        verify( spy ).launch( any( AcceptItem.class ),
                              eq( somewhere ),
                              isNull( Command.class ) );
        verify( spy ).onStart( eq( somewhere ) );
        verify( spy ).onReveal();

        verify( spy,
                times( 1 ) ).launch( any( AcceptItem.class ),
                                     eq( somewhere ),
                                     isNull( Command.class ) );
        verify( selectWorkbenchPartEvent,
                times( 1 ) ).fire( any( SelectPlaceEvent.class ) );
    }

    @Test
    //Reveal the same Place twice. It should be launched, OnStart and OnReveal called once.
    public void testGoToOnePlaceTwice() throws Exception {
        final PlaceRequest somewhere = new DefaultPlaceRequest( "Somewhere" );
        final PlaceRequest somewhereTheSame = new DefaultPlaceRequest( "Somewhere" );

        final WorkbenchScreenActivity activity = new MockWorkbenchScreenActivity( placeManager );
        final WorkbenchScreenActivity spy = spy( activity );

        when( activityManager.getActivities( somewhere ) ).thenReturn( new HashSet<Activity>( 1 ) {{
            add( spy );
        }} );

        placeManager.goTo( somewhere );
        placeManager.goTo( somewhereTheSame );

        verify( spy,
                times( 1 ) ).launch( any( AcceptItem.class ),
                                     eq( somewhere ),
                                     isNull( Command.class ) );
        verify( spy,
                times( 1 ) ).onStart( eq( somewhere ) );
        verify( spy,
                times( 1 ) ).onReveal();

        verify( selectWorkbenchPartEvent,
                times( 2 ) ).fire( any( SelectPlaceEvent.class ) );

    }

    @Test
    //Reveal two different Places. Each should be launched, OnStart and OnReveal called once.
    public void testGoToTwoDifferentPlaces() throws Exception {
        final PlaceRequest somewhere = new DefaultPlaceRequest( "Somewhere" );
        final PlaceRequest somewhereElse = new DefaultPlaceRequest( "SomewhereElse" );

        //The first place
        final WorkbenchScreenActivity activity1 = new MockWorkbenchScreenActivity( placeManager );
        final WorkbenchScreenActivity spy1 = spy( activity1 );

        //The second place
        final WorkbenchScreenActivity activity2 = new MockWorkbenchScreenActivity( placeManager );
        final WorkbenchScreenActivity spy2 = spy( activity2 );

        when( activityManager.getActivities( somewhere ) ).thenReturn( new HashSet<Activity>( 1 ) {{
            add( spy1 );
        }} );
        when( activityManager.getActivities( somewhereElse ) ).thenReturn( new HashSet<Activity>( 1 ) {{
            add( spy2 );
        }} );

        placeManager.goTo( somewhere );
        placeManager.goTo( somewhereElse );

        verify( spy1,
                times( 1 ) ).launch( any( AcceptItem.class ),
                                     eq( somewhere ),
                                     isNull( Command.class ) );
        verify( spy1,
                times( 1 ) ).onStart( eq( somewhere ) );
        verify( spy1,
                times( 1 ) ).onReveal();

        verify( spy2,
                times( 1 ) ).launch( any( AcceptItem.class ),
                                     eq( somewhereElse ),
                                     isNull( Command.class ) );
        verify( spy2,
                times( 1 ) ).onStart( eq( somewhereElse ) );
        verify( spy2,
                times( 1 ) ).onReveal();

        verify( selectWorkbenchPartEvent,
                times( 2 ) ).fire( any( SelectPlaceEvent.class ) );

    }

}
