#!/usr/bin/python
import sdk_common


# Block in charge of running integration tests (using Docker compose)
class SDKIntegrationTestRunner(sdk_common.BuildStepUsingGradle):
    def __init__(self, logger=None):
        super(SDKIntegrationTestRunner, self).__init__('SDK integration tests runner', logger)
        self.host = self.common_config.get_config().get_host()
        self.key_lab = self.common_config.get_config().get_apikey_lab()
        self.key_prod = self.common_config.get_config().get_apikey_prod()
        self.should_run_example = self.common_config.get_config().is_for_release()
        self.gradle_directory = self.common_config.get_config().get_sdk_example_directory()

    def execute(self):
        self.print_title()
        try:
            # self.log_info("Building test server docker image")
            # arguments = ["docker-compose", "build"]
            # return_code = self.call_command(arguments, None, True)
            # if return_code != 0:
            #     raise Exception('Error code', return_code)
            arguments = ["docker-compose", "up", "--build", "--no-recreate", "--exit-code-from",
                         "testrunner"]
            self.log_info("Running integration tests against integration lab")
            env = self.common_config.get_config().get_environment_with_host_set(self.host)
            env = self.common_config.get_config().get_environment_with_apikey_set(self.key_lab, env)
            return_code_int = self.call_command(arguments, None, True, True, env)
            if return_code_int != 0:
                self.log_warning("Failures have happened in Integration")
            env = self.common_config.get_config().get_environment_with_host_set("https://api.us-east-1.mbedcloud.com")
            env = self.common_config.get_config().get_environment_with_apikey_set(self.key_prod, env)
            self.log_info("Restarting containers to take into account environment changes")
            self.call_command(["docker-compose", "restart"], None, True, True, env)
            self.log_info("Running integration tests against production")
            return_code_prod = self.call_command(arguments, None, True, True, env)
            if return_code_prod != 0:
                self.log_warning("Failures have happened in Production")
            if return_code_int != 0 and return_code_prod != 0:
                raise Exception('Failures have happened in Integration and Production', return_code_int)
            if self.should_run_example:
                self.log_info("Running examples against integration lab")
                self.execute_gradle_task("test")
        except:
            self.log_error('Failed to successfully run all integration tests')
            return False
        self.log_info("Done.")
        return True