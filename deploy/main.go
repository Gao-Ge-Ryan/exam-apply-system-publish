package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	_ "os"
	"os/exec"
)

// GiteeWebhookHandler handles the incoming webhook requests.
func GiteeWebhookHandler() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read the request body
		_, err := ioutil.ReadAll(r.Body)
		if err != nil {
			http.Error(w, "Unable to read request body", http.StatusBadRequest)
			return
		}
		// Log the start of the script execution
		fmt.Println("Starting /opt/deploy.sh")
		// Execute the shell script
		cmd := exec.Command("/bin/bash", "/opt/deploy.sh")
		output, err := cmd.CombinedOutput()
		// If an error occurs during the script execution
		if err != nil {
			fmt.Printf("Error executing /opt/deploy.sh: %s\n", err)
			fmt.Printf("Output: %s\n", output)
		} else {
			// Log successful execution of the script
			fmt.Println("Successfully executed /opt/deploy.sh")
			fmt.Printf("Output: %s\n", output)
		}
		// Respond to the webhook
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Webhook received successfully"))
	}
}

func AdminGiteeWebhookHandler() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read the request body
		_, err := ioutil.ReadAll(r.Body)
		if err != nil {
			http.Error(w, "Unable to read request body", http.StatusBadRequest)
			return
		}
		// Log the start of the script execution
		fmt.Println("Starting /opt/deploy-admin.sh")
		// Execute the shell script
		cmd := exec.Command("/bin/bash", "/opt/deploy-admin.sh")
		output, err := cmd.CombinedOutput()
		// If an error occurs during the script execution
		if err != nil {
			fmt.Printf("Error executing /opt/deploy-admin.sh: %s\n", err)
			fmt.Printf("Output: %s\n", output)
		} else {
			// Log successful execution of the script
			fmt.Println("Successfully executed /opt/deploy-admin.sh")
			fmt.Printf("Output: %s\n", output)
		}
		// Respond to the webhook
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Webhook received successfully"))
	}
}

func ClientGiteeWebhookHandler() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read the request body
		_, err := ioutil.ReadAll(r.Body)
		if err != nil {
			http.Error(w, "Unable to read request body", http.StatusBadRequest)
			return
		}
		// Log the start of the script execution
		fmt.Println("Starting /opt/deploy-client.sh")
		// Execute the shell script
		cmd := exec.Command("/bin/bash", "/opt/deploy-client.sh")
		output, err := cmd.CombinedOutput()
		// If an error occurs during the script execution
		if err != nil {
			fmt.Printf("Error executing /opt/deploy-client.sh: %s\n", err)
			fmt.Printf("Output: %s\n", output)
		} else {
			// Log successful execution of the script
			fmt.Println("Successfully executed /opt/deploy-client.sh")
			fmt.Printf("Output: %s\n", output)
		}
		// Respond to the webhook
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Webhook received successfully"))
	}
}

func EnterpriseGiteeWebhookHandler() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read the request body
		_, err := ioutil.ReadAll(r.Body)
		if err != nil {
			http.Error(w, "Unable to read request body", http.StatusBadRequest)
			return
		}
		// Log the start of the script execution
		fmt.Println("Starting /opt/deploy-enterprise.sh")
		// Execute the shell script
		cmd := exec.Command("/bin/bash", "/opt/deploy-enterprise.sh")
		output, err := cmd.CombinedOutput()
		// If an error occurs during the script execution
		if err != nil {
			fmt.Printf("Error executing /opt/deploy-enterprise.sh: %s\n", err)
			fmt.Printf("Output: %s\n", output)
		} else {
			// Log successful execution of the script
			fmt.Println("Successfully executed /opt/deploy-enterprise.sh")
			fmt.Printf("Output: %s\n", output)
		}
		// Respond to the webhook
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Webhook received successfully"))
	}
}

func main() {
	// Set up the HTTP server
	http.HandleFunc("/webhook", GiteeWebhookHandler())
	http.HandleFunc("/webhook-admin", AdminGiteeWebhookHandler())
	http.HandleFunc("/webhook-client", ClientGiteeWebhookHandler())
	http.HandleFunc("/webhook-enterprise", EnterpriseGiteeWebhookHandler())
	fmt.Println("Listening on :8888 for Gitee Webhooks...")
	if err := http.ListenAndServe(":8888", nil); err != nil {
		fmt.Printf("Error starting server: %s\n", err)
	}
}
